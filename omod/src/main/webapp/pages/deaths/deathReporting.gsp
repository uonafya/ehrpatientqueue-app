<%
    ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])
    ui.includeJavascript("ehrconfigs", "jquery.dataTables.min.js")
    ui.includeJavascript("ehrconfigs", "bootstrap.min.js")
    ui.includeJavascript("ehrconfigs", "emr.js")
    ui.includeCss("ehrconfigs", "jquery.dataTables.min.css")
def menuItems = [
                    [
                        label: "Deceased",
                        href: ui.pageLink("patientqueueapp", "deaths/certifiedDeceasedList"),
                        iconProvider: "patientqueueapp",
                        icon: "morgue.png"
                    ],
                    [ label: "Back to home",
                      iconProvider: "kenyaui",
                      icon: "buttons/back.png",
                      href: ui.pageLink("kenyaemr", "userHome")
                    ]
            ]
%>
<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaemr", "patient/patientSearchForm", [ defaultWhich: "all" ]) }
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>
<div class="ke-page-content">
        ${ ui.includeFragment("kenyaemr", "patient/patientSearchResults", [ pageProvider: "patientqueueapp", page: "deaths/deathCertification" ]) }
</div>
<script type="text/javascript">
    jQuery(function() {
        jQuery('input[name="query"]').focus();
    });
</script>