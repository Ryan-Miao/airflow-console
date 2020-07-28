package com.ryanmiao.airflow.domain.dag.connector.contract;

import java.util.Map;
import lombok.Data;

//CHECKSTYLE:OFF
/**
 * Airflow rest api plugin返回值.
 * @author ryan
 * @date 19-10-22 下午7:54
 *
"airflow_cmd": "airflow backfill --start_date 2019-10-15 --end_date 2019-10-19 --ignore_first_depends_on_past poi_2_ryan_test",
"arguments": {
"api": "backfill",
"dag_id": "poi_2_ryan_test",
"end_date": "2019-10-19",
"ignore_first_depends_on_past": "True",
"start_date": "2019-10-15"
},
"call_time": "Tue, 22 Oct 2019 14:02:50 GMT",
"http_response_code": 200,
"output": {
"stderr": "/usr/local/lib/python3.7/site-packages/psycopg2/__init__.py:144: UserWarning: The psycopg2 wheel package will be renamed from release 2.8; in order to keep installing from binary please use \"pip install psycopg2-binary\" instead. For details see: <http://initd.org/psycopg/docs/install.html#binary-install-from-pypi>.\n  \"\"\")\n",
"stdin": "",
"stdout": ""
},
"post_arguments": {

},
"response_time": "Tue, 22 Oct 2019 18:01:04 GMT",
"status": "OK"
}
 */
@Data
@SuppressWarnings("PMD")
public class AirflowRestApiRs {
    //CHECKSTYLE:OFF
    private String airflow_cmd;
    private Map<String, Object> arguments;
    private String call_time;
    private Integer http_response_code;
    private Map<String, Object> output;
    private String status;

}
