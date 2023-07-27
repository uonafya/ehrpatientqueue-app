
<script type="text/javascript">
    jQuery(function() {
        populateDashboard();
        jq("#filter").click(function () {
            populateDashboard();
        });
    })
    function populateDashboard() {
        const summaryFromDate = jq('#summaryFromDate-field').val(),
            summaryToDate = jq('#summaryToDate-field').val();

        jq.getJSON('${ui.actionLink("patientqueueapp", "ProviderWorkLoad", "fetchEncountersForProviderEntry")}',
            {
                "fromDate" : summaryFromDate,
                "toDate" : summaryToDate,
            }
        ).success(function(data) {
            const totalPatients = data.totalPatients;
            const femalePatients = data.femalePatients;
            const malePatients = data.malePatients;

            // Update the HTML elements to display the patient statistics
            jq('.stat-digit').eq(0).html(totalPatients);
            jq('.stat-digit').eq(1).html(malePatients);
            jq('.stat-digit').eq(2).html(femalePatients);

            jQuery("#graph-container").highcharts({
                credits: {
                    enabled: false
                },
                chart: {
                    type: 'column',
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    type: 'category'
                },
                yAxis: {
                    title: {
                        text: 'Daily workload'
                    }
                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true,
                            format: '{point.y:.0f}'
                        }
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                    pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}</b><br/>'
                },
                series: [{
                    name: 'Statistics',
                    colorByPoint: true,
                    data: [{
                        name: 'Total Patients',
                        y:data.totalPatients,
                    }, {
                        name: 'Male Patients',
                        y: data.malePatients,
                    }, {
                        name: 'Female Patients',
                        y: data.femalePatients,
                    }]
                }],
            });
        });
    }
</script>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" />
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<style>
html, body, #graph-container {
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
}
.card-counter{
    box-shadow: 2px 2px 10px #DADADA;
    margin: 5px;
    padding: 20px 10px;
    background-color: #fff;
    height: 100px;
    border-radius: 5px;
    transition: .3s linear all;
}

.card-counter:hover{
    box-shadow: 4px 4px 20px #DADADA;
    transition: .3s linear all;
}

.card-counter.primary{
    background-color: #B0E0E6;
    color: black;
}

.card-counter.danger{
    background-color: #E6E6FA;
    color: black;
}

.card-counter.pham{
    background-color: #E0FFFF;
    color: black;
}

.card-counter.success{
    background-color: #A9FF96;
    color: black;
}

.card-counter.info{
    background-color: #FFA07A;
    color: black;
}

.card-counter i{
    font-size: 2.5em;
    opacity: 0.2;
}

.card-counter .count-numbers{
    position: absolute;
    right: 35px;
    top: 20px;
    font-size: 20px;
    display: block;
}

.card-counter .count-name{
    position: absolute;
    right: 35px;
    top: 65px;
    font-style: italic;
    text-transform: capitalize;
    opacity: 0.5;
    display: block;
    font-size: 15px;
}
</style>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">OPD Summaries</div>
    <div class="ke-panel-content">
        <br />
        <div class="row">
            <div class="col-12">
                <div style="margin-top: -1px " class="onerow">
                    <i class="icon-filter" style="font-size: 26px!important; color: #5b57a6"></i>
                    <label>&nbsp;&nbsp;From&nbsp;</label>${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'fromDate', id: 'summaryFromDate', label: '', useTime: false, defaultToday: false, class: ['newdtp']])}
                    <label>&nbsp;&nbsp;To&nbsp;</label  >${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'toDate',    id: 'summaryToDate',   label: '', useTime: false, defaultToday: false, class: ['newdtp']])}
                    <button id="filter" type="button" class=" btn btn-primary right">${ui.message("Filter")}</button>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <hr />
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="row">

                    <div class="col-md-4">
                        <div class="card-counter danger">
                            <i class="fa fa-users"></i>
                            <span class="count-name stat-text">TOTAL PATIENTS</span>
                            <span class="count-numbers stat-digit"></span>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card-counter pham">
                            <i class="fa fa-plus-circle"></i>
                            <span class="count-name stat-text">MALE</span>
                            <span class="count-numbers stat-digit"></span>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card-counter success">
                            <i class="fa fa-flask"></i>
                            <span class="count-name stat-text">FEMALE</span>
                            <span class="count-numbers stat-digit"></span>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div id="graph-container" style="min-width: 100%; height:400px; margin: 0; padding-bottom: 5px;">

        </div>
    </div>
</div>

<script type="text/javascript">

</script>