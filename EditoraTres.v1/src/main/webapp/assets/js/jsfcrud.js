function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}
function scrollToBottom() {
    $('.ui-datatable-scrollable-body').scrollTop(100000)
}

var scrollPosition;
var scrollPosition2;

function saveScrollPosition() {
    scrollPosition = $('#idboleto_pendentes').scrollTop();
    scrollPosition2 = $('#idboleto_pagos').scrollTop();

}

function setScrollPosition() {
    $('#idboleto_pendentes').scrollTop(scrollPosition);
    $('#idboleto_pagos').scrollTop(scrollPosition2);
}

function autoScrollPDatatable(idDataTable) 
{
    // for selection in JQuery the ids with : must be endoded with \\:
    var primfcid = idDataTable.replace(':', '\\:');
    var idDataTbl = '#' + primfcid;
    var idDataContainer = idDataTbl  + "_data";

    var totalHeight = $(idDataTbl + " .ui-datatable-scrollable-body").height();
    var lichildren = $(idDataContainer).children("tr");
    var itemHeight = $(idDataContainer).children("tr").height();
    var anzItems = lichildren.length;
    var anzVisItems = totalHeight / itemHeight;
    var selItem = detectDataTableScrollPos(lichildren); 
    if(selItem == -1)
    {
        // no selection found...
        return;
    }

    var maxScrollItem = anzItems - anzVisItems;
    var scrollTopInPx; 
    if(selItem >= maxScrollItem)
    {
        // scroll table to the bottom
        scrollTopInPx = maxScrollItem * itemHeight;
    }
    else if(selItem < 2)
    {
        // scroll table to the top
        scrollTopInPx = 0;
    }
    else
    {
        // scroll selected item to the 1.2 th position
        scrollTopInPx = (selItem - 1.2) * itemHeight;
    }

    $(idDataTbl + " .ui-datatable-scrollable-body").animate({scrollTop:scrollTopInPx}, scrollTopInPx);  
}


function detectDataTableScrollPos(liChildren)
{
    for (i=0;i< liChildren.length;++i)
    {
        var chd = liChildren[i];
        var x = chd.getAttribute("aria-selected");
        if(x === "true")
        {
            return i;
        }
    }
    return -1;
}
                    