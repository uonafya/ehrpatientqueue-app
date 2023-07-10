<%
    ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])


def menuItems = [
                    [ label: "Back to home",
                      iconProvider: "kenyaui",
                      icon: "buttons/back.png",
                      href: ui.pageLink("kenyaemr", "userHome")
                    ]
            ]
%>
<div class="ke-page-sidebar">
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