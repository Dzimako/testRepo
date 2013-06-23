jQuery(function (jQuery) {
    var pktb = $('.pktb');
    //pktb.find("button.pktb-reportPlaceholderSubmit").button();
    pktb.find("button.pktb-reportPlaceholderSubmit").bind("click", {reportId: pktb_reportId}, function(e){
        //  jQuery.post(pktb_context + '/reports/showById.action', {reportId: pktb_reportId},function(){
        pktb.find('.report .body').load(pktb_context + 'reports/getReport.action', {id: e.data.reportId});
        //  });
    });
});