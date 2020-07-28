package com.ryanmiao.airflow.domain.ext.service.impl;

import com.ryanmiao.airflow.config.GitProperties;
import com.ryanmiao.airflow.domain.ext.service.IPyGenerator;
import com.ryanmiao.airflow.domain.ext.vo.GeneratedPyRq;
import com.ryanmiao.airflow.exception.BizException;
import com.ryanmiao.airflow.log.SystemEvent;
import com.ryanmiao.airflow.security.vo.UserInfo;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author ryan
 * @date 19-9-19 下午4:24
 */
@Service
public class PyGeneratorImpl implements IPyGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PyGeneratorImpl.class);

    private GitProperties gitProperties;

    public PyGeneratorImpl(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Override
    public String push(GeneratedPyRq rq) {
        try {
            return pushToGit(rq);
        } catch (Exception ex) {
            throw new BizException(ex.getMessage(),
                SystemEvent.PUSH_FILE_REMOTE_GIT_FAILED.getCode());
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            doDeleteFile(fileName);
        } catch (Exception ex) {
            throw new BizException(ex.getMessage(),
                SystemEvent.DELETE_FILE_REMOTE_GIT_FAILED.getCode());
        }
    }

    private void doDeleteFile(String fileName) throws IOException, GitAPIException {
        Git git = getGit();
        String repoDir = git.getRepository().getDirectory().getParent();
        File file = new File(repoDir + File.separator + fileName);
        if (file.exists()) {
            boolean delete = file.delete();
            if (!delete) {
                throw new IOException("Can not deleteGit file: " + file);
            } else {
                LOGGER.info("{} deleteGit file: {}", getUser().getUsername(), file);
            }
        }

        git.rm().addFilepattern(fileName).call();
        pushChange(git, "deleteGit " + fileName);
    }

    private String pushToGit(GeneratedPyRq rq) throws IOException, GitAPIException {
        Git git = getGit();

        //create dir for category
        //create python file
        String repoDir = git.getRepository().getDirectory().getParent();
        File categoryDir = new File(repoDir, rq.getCategoryName());
        if (!categoryDir.exists() && !categoryDir.mkdir()) {
            throw new IOException("Could not create dir: " + categoryDir);
        }

        String dagFileName = categoryDir + File.separator + rq.getDagFileName();
        File dagFile = new File(dagFileName);
        if (dagFile.exists()) {
            boolean delete = dagFile.delete();
            if (!delete) {
                throw new IOException("Could not deleteGit old file " + dagFile);
            }
        }

        // write file
        Writer writer = new OutputStreamWriter(new FileOutputStream(dagFile),
            StandardCharsets.UTF_8);

        writer.write(rq.getDagPyBody());
        writer.flush();
        writer.close();

        String filepattern = rq.getCategoryName() + File.separator + rq.getDagFileName();
        git.add().addFilepattern(filepattern).call();

        pushChange(git, "add " + filepattern);

        // clean up here to not keep using more and more disk-space for these samples
        return gitProperties.getRemote().replace(".git", "") + "/blob/master/" + rq
            .getCategoryName() + "/" + rq
            .getDagFileName();
    }

    private void pushChange(Git git, String msg) throws GitAPIException, IOException {
        Status status = git.status().call();
        LOGGER.info("status: {}, {}, {}", status.getAdded(), status.getChanged(),
            status.getUncommittedChanges());
        if (status.isClean()) {
            throw new IOException("nothing changed");
        }

        // get current user for commit message
        UserInfo userInfoV3 = getUser();

        LOGGER.info("git add {}", msg);
        git.commit().setAuthor(userInfoV3.getUsername(), userInfoV3.getEmail())
            .setMessage(userInfoV3.getUsername() + " commit " + msg)
            .call();
        LOGGER.info("git commit");

        git.push()
            .setProgressMonitor(new SimpleProgressMonitor())
            .setCredentialsProvider(getCredentialsProvider()).call();

        LOGGER.info("git push");
        git.close();
        LOGGER.info("git close");
        FileUtils.deleteDirectory(git.getRepository().getDirectory().getParentFile());
    }

    private UserInfo getUser() {
        return (UserInfo) SecurityContextHolder.getContext().getAuthentication()
            .getDetails();
    }

    private Git getGit() throws IOException, GitAPIException {
        File localPath = File.createTempFile("TestGitRepository", "");
        if (!localPath.delete()) {
            throw new IOException("Could not deleteGit temporary file " + localPath);
        }
        // then clone
        LOGGER.info("Cloning from {} to {} ", gitProperties.getRemote(), localPath);

        Git git = Git.cloneRepository()
            .setURI(gitProperties.getRemote())
            .setCredentialsProvider(getCredentialsProvider())
            .setDirectory(localPath)
            .setProgressMonitor(new SimpleProgressMonitor())
            .call();

        LOGGER.info("Having repository: {} ", git.getRepository().getDirectory());
        return git;
    }

    private CredentialsProvider getCredentialsProvider() {
        return new UsernamePasswordCredentialsProvider(gitProperties.getUsername(),
            gitProperties.getPassword());
    }

    private static class SimpleProgressMonitor implements ProgressMonitor {

        @Override
        public void start(int totalTasks) {
            LOGGER.debug("Starting work on " + totalTasks + " tasks");
        }

        @Override
        public void beginTask(String title, int totalWork) {
            LOGGER.debug("Start " + title + ": " + totalWork);
        }

        @Override
        public void update(int completed) {
            LOGGER.debug(completed + "-");
        }

        @Override
        public void endTask() {
            LOGGER.debug("Done");
        }

        @Override
        public boolean isCancelled() {
            return false;
        }
    }
}
