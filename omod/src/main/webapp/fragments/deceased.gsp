<%
    ui.includeCss("ehrconfigs", "referenceapplication.css")
%>
<script type="text/javascript">
 var jq = jQuery;
     jq(function () {
     var table = jQuery("#deceasedTbl").DataTable({
               dom: 'Bfrtip',
               buttons: ['copy', 'csv', 'excel',
                   {
                       extend: 'print',
                       messageTop: 'Deceased Cases',
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

           jQuery('#deceasedTbl tbody').on( 'click', 'tr', function () {
               console.log( table.row( this ).data() );
           } );

     });
</script>
<div class="ke-page-content">
    <div class="ke-panel-frame">
         <div class="ke-panel-heading">Confirmed Deceased Cases</div>
             <div class="ke-panel-content">
                <table border="0" cellpadding="0" cellspacing="0" id="deceasedTbl" width="100%">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Date and Time of Death</th>
                            <th>Cause of Death Coded</th>
                            <th>Cause of Death Non Coded</th>
                            <th>Entry date and time</th>
                            <th>Status</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (allDeceasedAndConfirmedCases.empty) { %>
                            <tr>
                                <td colspan="7">
                                    No records found for specified period
                                </td>
                            </tr>
                        <% } %>
                        <% if (allDeceasedAndConfirmedCases) { %>
                            <% allDeceasedAndConfirmedCases.each {%>
                                <tr>
                                    <td>${it.name}</td>
                                    <td>${it.dOfDeath}</td>
                                    <td>${it.causeOfDeath}</td>
                                    <td>${it.causeOfDeathNonCodded}</td>
                                    <td>${it.entryTime}</td>
                                    <td>${it.status}</td>
                                </tr>
                            <%}%>
                        <%}%>
                    </tbody>
                </table>
            </div>
         </div>
    </div>
</div>