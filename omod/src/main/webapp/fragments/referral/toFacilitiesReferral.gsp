<script>
    var jq = jQuery;
    jq(function () {
        var tbl = jq('#referralsToOtherFacilitiesTbl').DataTable();
    });
</script>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Referrals to other facilities</div>
    <div class="ke-panel-content" style="background-color: #F3F9FF;">
            <table id="referralsToOtherFacilitiesTbl" width="100%">
                <thead>
                  <tr>
                      <th>Patient Identifier</th>
                      <th>Patient Name</th>
                      <th>Referral Reason</th>
                      <th>Referred Date</th>
                      <th>Referring Doctor</th>
                      <th>Facility code referred to</th>
                      <th>Facility name referred to</th>
                      <th>Clinical Notes</th>
                  </tr>
                </thead>
                <tbody>
                    <% toFacilityReferrals.each { %>
                        <tr>
                            <td>${it.patientIdentifier}</td>
                            <td>${it.patientNames}</td>
                            <td>${it.referralReason}</td>
                            <td>${it.dateCreated}</td>
                            <td>${it.creator}</td>
                            <td>${it.facilityCodeReferredTo}</td>
                            <td>${it.facilityNameReferredTo}</td>
                            <td>${it.referralNotes}</td>
                        </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
</div>
