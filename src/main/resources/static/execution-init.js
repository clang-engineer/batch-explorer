const initExecutions = () => {
  fetch('api/job/executions')
  .then(response => response.json())
  .then(data => {
    const jobExecutions = document.getElementById('job-executions');

    for (let i = 0; i < data.length; i++) {
      jobExecutions.prepend(createExecutionElement(data[i]));
    }
  });
}

const createExecutionElement = (execution) => {
  const jobExecutionElement = document.createElement('div');
  jobExecutionElement.setAttribute('class', 'col-4')

  jobExecutionElement.innerHTML = `
      <div style="border: 1px solid black; padding: 10px; margin: 10px;">
        <div>id: ${execution.id}</div>
        <div>jobName: ${execution.jobName}</div>
        <div>status: ${execution.status}</div>
        <div>startTime: ${execution.startTime}</div>
        <div>endTime: ${execution.endTime}</div>
        <div>exitCode: ${execution.exitCode}</div>
        <div>exitDescription: ${execution.exitDescription}</div>
        <div>lastUpdated: ${execution.lastUpdated}</div>
        <div class="button-group">
        <button onclick="fetch('api/job/executions/${execution.id}/stop', {method: 'POST'})">stop</button>
        <button onclick="fetch('api/job/executions/${execution.id}/restart', {method: 'POST'})">restart</button>
        <button onclick="fetch('api/job/executions/${execution.id}', {method: 'DELETE'})">delete</button>
        </div>
      </div>
      `;

  return jobExecutionElement;
}

