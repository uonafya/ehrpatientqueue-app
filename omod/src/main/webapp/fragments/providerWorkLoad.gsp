<script type="text/javascript">
    jQuery(function() {
        updateTable();
        jq("#filterDoctorPatientSummaries").click(function () {
            updateTable();
        });
    });
    function updateTable() {
        fetchDoctorPatientSummariesByDateRange(moment(jq("#summaryFromDate-field").val()).format('DD/MM/YYYY'), moment(jq('#summaryToDate-field').val()).format('DD/MM/YYYY'), jq('#doctorPatientProvider').val());
    }
    function fetchDoctorPatientSummariesByDateRange(fromDate, toDate, provider) {
        var toReturn;
        jQuery.ajax({
            type: "GET",
            url: '${ui.actionLink("financials", "doctorsSummaries", "fetchPatientsPerProviderEntry")}',
            dataType: "json",
            global: false,
            async: false,
            data: {
                fromDate: fromDate,
                toDate: toDate,
                provider: provider
            },
            success: function (data) {
                toReturn = data;
            }
        });
        return populateTableBodyForDoctorPatientSummaryItems(toReturn);
    }
    function populateTableBodyForDoctorPatientSummaryItems(data) {
        jQuery("#doctorPatientDetailSummaries").DataTable().clear().destroy();
        data.map((item) => {
            jQuery("#doctorPatientSummaryItems").append("<tr><td>" + item.patientIdentifier + "</td><td>" + item.patientNames + "</td><td>" + item.dob + "</td><td>" + item.age +"</td><td>" + item.sex +"</td><td>" + item.encounterDate +"</td><td>" + "<button class='button task'>VIEW</button>" +"</td></tr>");
        });

        var table = jQuery("#doctorPatientDetailSummaries").DataTable({
            dom: 'Bfrtip',
            buttons: ['copy', 'csv', 'excel',
                {
                    extend: 'print',
                    messageTop: 'Doctor Patient Summaries.',
                    customize: function (win) {
                        jq(win.document.body)
                            .prepend(`${ ui.includeFragment("patientdashboardapp", "printHeader") }`);
                    },
                    repeatingHead: {
                        logo: '${ui.resourceLink('ehrinventoryapp', 'images/kenya_logo.bmp')}',
                        logoPosition: 'center',
                        logoStyle: ''
                    },
                    title: ''
                }
            ]
        });

        jQuery('#doctorPatientDetailSummaries tbody').on( 'click', 'tr', function () {
            var table =  jq("#doctorPatientDetailSummaries").DataTable();

            var info = table.row(this).data();
            ui.navigate('financials', 'patientVisitSummaries', {identifier: info[0]});

        } );

    }
</script>
<style>
table#nhifDetails.dataTable tbody tr:hover {
    background-color: #43fff8;
}
</style>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">OPD Summaries </div>
    <div class="ke-panel-content">
        <div style="margin-top: -1px " class="onerow">
            <i class="icon-filter" style="font-size: 26px!important; color: #5b57a6"></i>
            <label>&nbsp;&nbsp;From&nbsp;</label>${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'fromDate', id: 'summaryFromDate', label: '', useTime: false, defaultToday: false, class: ['newdtp']])}
            <label>&nbsp;&nbsp;To&nbsp;</label  >${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'toDate',    id: 'summaryToDate',   label: '', useTime: false, defaultToday: false, class: ['newdtp']])}
            <label>&nbsp;&nbsp;Provider&nbsp;</label>

            <label>&nbsp;&nbsp;&nbsp;</label><button id="filterDoctorPatientSummaries" type="button" class=" btn btn-primary right">${ui.message("Filter")}</button>
        </div>
        <hr />
        <table border="0" cellpadding="0" cellspacing="0" id="doctorPatientDetailSummaries" width="100%">
            <thead>
            <tr>
                <th>Identifier</th>
                <th>Patient Name</th>
                <th>DOB</th>
                <th>Age</th>
                <th>Sex</th>
                <th>Visit Date</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody id="doctorPatientSummaryItems"></tbody>
        </table>
    </div>
</div>
