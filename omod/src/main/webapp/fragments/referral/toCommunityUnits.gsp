<script>
    var jq = jQuery;
    jq(function () {
        var tbl = jq('#referralsToCommunity').DataTable();
    });
</script>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Referrals to Community Units</div>
    <div class="ke-panel-content" style="background-color: #F3F9FF;">
            <table id="referralsToCommunity" width="100%">
                <thead>
                  <tr>
                      <th>Patient Identifier</th>
                      <th>Patient Name</th>
                      <th>Referral Reason</th>
                      <th>Referred Date</th>
                      <th>Referring Doctor</th>
                      <th>Community code referred to</th>
                      <th>Community name referred to</th>
                      <th>Clinical Notes</th>
                  </tr>
                </thead>
                <tbody>
                    <% toCommunityReferrals.each { %>
                        <tr>
                            <td>${it.patientIdentifier}</td>
                            <td>${it.patientNames}</td>
                            <td>${it.referralReason}</td>
                            <td>${it.dateCreated}</td>
                            <td>${it.creator}</td>
                            <td>${it.communityUnitCodeReferredTo}</td>
                            <td>${it.communityUnitNameReferredTo}</td>
                            <td>${it.referralNotes}</td>
                        </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
</div>