
<script>
    var jq = jQuery;
    jq(function () {
        var tbl = jq('#shrHistoryTbl').DataTable();

        var patientHistoryDialog = emr.setupConfirmationDialog({
            dialogOpts: {
                overlayClose: false,
                close: true
            },
            selector: '#view-patient-history-dialog',
            actions: {
                cancel: function () {
                    jq.getJSON('${ui.actionLink("patientqueueapp", "referralHistory", "getObservationPerEncounter")}',
                        {
                            encounterId: jq("#encounterId").val()
                        }
                    ).success(function (data) {
                        populateTableHistorySummary(data);
                        patientHistoryDialog.close();
                        location.reload();
                    });
                }
            }
        });

        jq('#shrHistoryTbl tbody').on( 'click', 'tr', function () {
              e.preventDefault();
              var values = tbl.row(this).data();
              var rq = values[0];
              jQuery('#encounterId').val(rq);
              patientHistoryDialog.show();
          } );
    });
    function populateTableHistorySummary(data) {
        jQuery("#historySummary").DataTable().clear().destroy();

        jQuery("#historySummaryItems").empty();

        data.map((item) => {

          jQuery("#historySummaryItems").append("<tr><td>" + item + "</td><td>" + item + "</td></tr>");
        });
</script>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Patient Clinical History</div>
    <div class="ke-panel-content" style="background-color: #F3F9FF;">
        <table id="shrHistoryTbl">
            <thead>
              <tr>
                  <th style="display: none;">ID</th>
                  <th>Visit Date</th>
                  <th>Provider</th>
                  <th>Date Created</th>
                  <th>Facility</th>
                  <th>Action</th>
              </tr>
            </thead>
            <tbody>
                <% encounters.each { %>
                    <tr>
                        <td style="display: none;">${it.encounterId}</td>
                        <td>${it.encounterDatetime}</td>
                        <td>${it.creator.person.names.fullName}</td>
                        <td>${it.dateCreated}</td>
                        <td>${it.location.name}</td>
                        <td>View more...</td>
                    </tr>
                <%}%>
            </tbody>
        </table>
    </div>
    <div id="view-patient-history-dialog" class="dialog" style="display:none;">
        <div class="dialog-header">
            <i class="icon-folder-open"></i>
            <h3>Patient Encounter History</h3>
        </div>
        <input type="text" id="encounterId" name="encounterId" />
        <div class="dialog-content">
            <div>
              <table border="0" cellpadding="0" cellspacing="0" id="historySummary" width="100%">
                  <thead>
                  <tr>
                      <th>Question</th>
                      <th>Observation</th>
                      <th>Comments</th>
                  </tr>
                  </thead>
                  <tbody id="historySummaryItems"></tbody>
              </table>
            </div>
            <div class="onerow">
                <button class="button cancel right">Cancel</button>
            </div>
        </div>
    </div>
</div>
