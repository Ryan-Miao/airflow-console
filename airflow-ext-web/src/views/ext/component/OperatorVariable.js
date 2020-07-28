
export const variableDesc = '可以使用变量，比如task执行日期是2019-09-24\n{{ ds_nodash }} 表示  20190924\n' +
  '{{ ds }} 表示 2019-09-24\n' +
  '{{ macros.ds_add(ds, 7)}} 表示7天后  2019-10-01\n' +
  '{{ macros.ds_add(ds, -5) }} 表示5天前 2019-09-19\n' +
  '{{ execution_date.strftime("%d-%m-%Y") }} 自己格式化日期 24-09-2019 \n' +
  '{{ (execution_date - macros.timedelta(days=5)).strftime("%Y-%m-%d") }} 表示5天前，并自己格式化日期 2019-09-19\n' +
  '更多变量用法,参见: https://airflow.apache.org/docs/stable/macros.html'
