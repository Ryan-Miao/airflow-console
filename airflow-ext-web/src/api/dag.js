import request from '@/utils/request-api'

export function getDagList(data) {
  return request({
    url: '/v1/dag/list',
    method: 'post',
    data
  })
}

export function deleteGit(id) {
  return request({
    url: '/v1/dag/delete-git',
    method: 'delete',
    params: {
      id: id
    }
  })
}

export function deleteRecord(id) {
  return request({
    url: '/v1/dag/delete-record',
    method: 'delete',
    params: {
      id: id
    }
  })
}

/**
 *  回填dag
 * @param params  http://test-airflow-compass.ryan-miao.com/admin/rest_api/api?
 * api=backfill
 * &dag_id=poi_2_ryan_test
 * &start_date=2019-10-15
 * &end_date=2019-10-19
 * &ignore_first_depends_on_past=True
 * dag_id - string - The id of the dag

 task_regex (optional) - string - The regex to filter specific task_ids to backfill (optional)
 start_date (optional) - string - Override start_date YYYY-MM-DD. Either this or the end_date needs to be provided.
 end_date (optional) - string - Override end_date YYYY-MM-DD. Either this or the start_date needs to be provided.
 mark_success (optional) - boolean - Mark jobs as succeeded without running them
 local (optional) - boolean - Run the task using the LocalExecutor
 donot_pickle (optional) - boolean - Do not attempt to pickle the DAG object to send over to the workers, just tell the workers to run their version of the code.
 include_adhoc (optional) - boolean - Include dags with the adhoc argument.
 ignore_dependencies (optional) - boolean - Ignore task-specific dependencies, e.g. upstream, depends_on_past, and retry delay dependencies
 ignore_first_depends_on_past (optional) - boolean - Ignores depends_on_past dependencies for the first set of tasks only (subsequent executions in the backfill DO respect depends_on_past)
 subdir (optional) - string - File location or directory from which to look for the dag
 pool (optional) - string - Resource pool to use
 dry_run (optional) - boolean - Perform a dry run
 */
export function backfill(data) {
  // param check
  if (!data.dag_id) {
    throw new Error('dag_id必须')
  }
  if (!data.start_date) {
    throw new Error('start_date必须')
  }
  if (!data.end_date) {
    throw new Error('end_date必须')
  }
  return request({
    url: '/v1/dag/rest-api?api=backfill',
    method: 'post',
    data
  })
}
