if (typeof pktb === 'undefined')
    var pktb = { logs:'' };

pktb.init = function (vars) {

    var ns = vars.ns,
        cr = vars.cr,
        cn = vars.cn, // cn текущего юзера
        currentUser = vars.currentUser,
        userServiceURL = vars.userServiceURL,
        log = typeof console != 'undefined' ? function (x) {
            console.log(x);
        } : function (x) {
            $('.logs').append('<br>' + x);
        },
        dir = typeof console != 'undefined' ? function (x) {
            console.dir(x);
        } : function () {
        },
        _confirm = function (text, ok, cancel, title) {
            var dialog = $('<div>' + text + '</div>').addClass('pktb').appendTo(document.body);
            dialog.dialog({
                title:title,
                resizable:false,
                modal:true,
                buttons:{
                    'ОК':function () {
                        dialog.dialog('close').remove();
                        if (typeof ok != 'undefined') ok();
                    },
                    'Отмена':function () {
                        dialog.dialog('close').remove();
                        if (typeof cancel != 'undefined') cancel();
                    }
                }
            });
        },
        alert = function (text, title) {
            var dialog = $('<div>' + text + '</div>').addClass('pktb').appendTo(document.body);
            dialog.dialog({
                title:title,
                resizable:false,
                modal:true,
                buttons:{
                    'ОК':function () {
                        dialog.dialog('close').remove();
                    }
                }
            });
        };

    // Корневой элемент
    var pktb = $('#' + ns + 'pktb');

    // Логи
    pktb.bind('ajaxStop', function (a) {
        log('AJAX:STOP');
    });
    pktb.bind('ajaxStart', function (a) {
        log('AJAX:START');
    });

    // Общие
    var commons = function () {
        $.datepicker.setDefaults($.datepicker.regional['ru']);
        pktb.find('input[data-widget="datepicker"]').datepicker({
            showOn:'button',
            buttonImage:cr + 'img/calendar.png',
            buttonImageOnly:true
        }).removeAttr('data-widget');
        pktb.find('[data-widget="button"]').each(function () {
            var $this = $(this);
            $this.button({
                text:$this.data('text'),
                icons:{
                    primary:$this.data('icon')
                }
            })
        }).removeAttr('data-widget');
        pktb.find('.hover-cursor-pointer').mouseover(function(){

        })
    };
    try {
        commons();
        log('COMMONS:OK')
    } catch (e) {
        log('COMMONS:FAIL');
        log(e.message);
    }

    // Лейаут
    try {
        var resizer1 = pktb.find('.resizer1').live('dblclick',function () {
            pktb.find('.east').toggle()
        }).live('mousedown', function (e) {
                pktb.bind('mouseup mouseleave', function (e) {
                    pktb.removeClass('unselectable').css({cursor:'auto'});
                    pktb.unbind('mousemove')
                });
                var size = e.clientX;
                pktb.addClass('unselectable').css({cursor:'e-resize'});
                pktb.bind('mousemove', function (e) {
                    var east = pktb.find('.east');
                    east.width(east.width() + e.clientX - size);
                    size = e.clientX;

                })
            });

        var resizer2 = pktb.find('.resizer2').live('mousedown', function (e) {
            pktb.bind('mouseup mouseleave', function (e) {
                pktb.removeClass('unselectable').css({cursor:'auto'});
                pktb.unbind('mousemove')
            });
            var size = e.clientY;
            pktb.addClass('unselectable').css({cursor:'n-resize'});
            pktb.bind('mousemove', function (e) {
                var top = pktb.find('.filter:first');
                top.height(top.height() + e.clientY - size);
                size = e.clientY;
            })
        });
        log('LAYOUT:OK')
    } catch (e) {
        log('LAYOUT:FAIL');
        log(e.message)
    }

    pktb.initReportTab = function (id, folder, available) {
        var insertFilter = function (id) {
            if (available)
                pktb.getFiltersHTML(id);
            else
                pktb.getFiltersHTMLUnavailable();
        };

        try {
            if (!folder)
                insertFilter(id);
            var reportPktb = pktb.find('.report'),
                reportScope = 0,
                $reportScope = reportPktb.find('.tabs'),
                reportScopes = $reportScope.children(':not(.clear-fix)').removeClass('selected').find('reportFilter').addClass('selected').end()
                    .unbind().bind('click', function () {
                        pktb.find('.report .body').empty();
                        reportScopes.removeClass('selected');
                        $(this).addClass('selected');
                        reportScope = reportScopes.index(this);
                        if ($(this).hasClass('reportFilter') && !folder) {
                            insertFilter(id);
                        } else if ($(this).hasClass('statistics')) {
                            pktb.insertStatisticsFilterInContainer(pktb.find('.report .filter'), {id:id});
                        } else if ($(this).hasClass('rights')) {
                            pktb.insertRightsFilterInContainer(pktb.find('.report .filter'), {id:id});
                        } else {
                            pktb.find('.report .filter').empty();
                            pktb.find('.report .body').empty();
                        }
                    });
            log('report TABS:OK');
        } catch (e) {
            log('report TABS:FAIL');
            log(e.message)
        }
    };

    // Табы
    try {
        var tabs = pktb.find('.tabs')
            .children(':not(.clear-fix)').click(function () {
                var $this = $(this);
                $this.addClass('selected').siblings().removeClass('selected')
                pktb.tree.deselect_all()
            })
            .end();
        log('TABS:OK');
    } catch (e) {
        log('TABS:FAIL');
        log(e.message);
    }

    // Дерево и избранное
    try {

        var select = pktb.find('#allOrFavourites')
            .tabs({
                select: function() {
                    pktb.find('#favourites .favourites-report-clicked').removeClass('favourites-report-clicked')
                    tree.jstree('deselect_all')
                }
            })




        var updateFavourites = function() {
            $.getJSON(cr + '/reports/getFavourites.action', {cn : cn} , function(data) {
                pktb.favourites = data;
                var html = ''
                $.each(data, function() {
                    html += '<div class="favourites-report"><span class="favourites-report-icon"></span><span class="favourites-report-name">' + this.name + '</span></div>'
                })
                $('#favourites')
                    .html(html)
                    .find('.favourites-report')
                    .each(function(i) {
                        $(this).click(function(){
                            $(this)
                                .toggleClass('favourites-report-clicked')
                                .siblings()
                                .removeClass('favourites-report-clicked')
                            renderReportFilter(data[i])
                        })
                    })
            })

        }

        updateFavourites()

        var renderReportFilter= function(report) {

            pktb.selectedReportId = report.id

            var $report = pktb.find('.report')
            if ($report.is('.adminRights'))
                $report.find('.rights, .statistics').show();

            $report.find('.name')
                .text(report.name);


            if ($report.is('.adminRights, .listenerRights')) {
                $report.find('.filter, .body')
                    .empty();

                pktb.initReportTab(report.id, false, report.available);
                tabs
                    .children()
                    .first()
                    .addClass('selected')
                    .siblings()
                    .removeClass('selected');
            }
        }

        pktb.selectedReportId = null;
        var tree = pktb.find('.tree').jstree({
            plugins:['themes', 'json_data', 'ui', 'crrm'/*, 'contextmenu', 'sort' */],
            /* sort: function(a, b) {
             var name1 = this.get_text(a),
             name2 =  this.get_text(b),
             num1 = name1.split(' ')[0].split('.'),
             num2 = name2.split(' ')[0].split('.')

             for (i = 0; i < Math.min(num1.length, num2.length); i++) {
             var a = num1[i].match(/\d+/),
             b = parseInt(num2[i].match(/\d+/))

             if (a > b)
             return 1
             else if (a < b)
             return -1
             }

             return 0
             }, */
            json_data:{
                progressive_render:true,
                ajax:{
                    url:cr + '/reports/getByParentReportId.action',
                    data:function (n) {
                        return {
                            getByParentReportIdRequest:JSON.stringify({ id:(n.attr ? n.attr('id') : 0 ), userBean:currentUser})
                        };
                    },
                    success:function (data) {
                        log('TREE.GET:SUCCESS');
                        return $.map(data, function (report) {
                            var node = {};
                            node.attr = {
                                id:report.id,
                                'class':(report.folder ? 'folder-node' : (report.available ? 'report-node' : 'report-node-unavailable'))
                            };
                            node.metadata = {
                                id:report.id,
                                name:report.name,
                                available:report.available,
                                type:(report.folder ? 'folder' : 'report')
                            };
                            node.data = report.name;
                            report.folder && (node.state = 'closed');
                            log('TREE.FORM:SUCCESS TITLE=' + node.data);
                            return node;
                        })
                    }
                }
            },
            ui:{ select_limit:1 }
        })
            .bind('select_node.jstree', function (e, data) {
                var id = $.data(data.rslt.obj[0], 'id'),
                    name = $.data(data.rslt.obj[0], 'name'),
                    type = $.data(data.rslt.obj[0], 'type'),
                    available = $.data(data.rslt.obj[0], 'available');

                pktb.selectedReportId = id;

                if (pktb.find('.report').hasClass('adminRights')) {
                    pktb.find('.report .rights').show();
                }
                if (type === 'folder') {
                    pktb.find('.report .name').text(REPORT_NOT_SELECTED);
                    if (pktb.find('.report').hasClass('adminRights')) {
                        pktb.find('.report .statistics').hide();
                    }
                    if (pktb.find('.report').hasClass('adminRights') || pktb.find('.report').hasClass('listenerRights')) {
                        pktb.find('.report .filter').empty();
                        pktb.find('.report .body').empty();
                    }
                    pktb.initReportTab(id, true, available);
                    tree.jstree("toggle_node", data.rslt.obj);
                    createReportFolder
                        .find('.where').text(name).end()
                        .find('[name="parentReportId"]:hidden').val(id)
                }
                else {
                    pktb.find('.report .name').text(name);
                    if (pktb.find('.report').hasClass('adminRights')) {
                        pktb.find('.report .statistics').show();
                    }
                    if (pktb.find('.report').hasClass('adminRights') || pktb.find('.report').hasClass('listenerRights')) {
                        pktb.find('.report .filter').empty();
                        pktb.find('.report .body').empty();
                        pktb.initReportTab(id, false, available);
                        tabs.children().first().addClass('selected').siblings().removeClass('selected');
                    }
                }
            })
            .bind('deselect_node.jstree', function (e, data) {
                createReportFolder
                    .find('.where').text('Корневая папка').end()
                    .find('[name="parentReportId"]:hidden').val(0)
            })
            .bind('remove.jstree', function (e, data) {
                $.post(
                    cr + '/reports/delete.action',
                    {
                        id:$.data(data.rslt.obj[0], 'id')
                    },
                    function (id, status) {
                        if (status != 'success')
                            $.jstree.rollback(data.rlbk);
                    }
                );
            });

        tree.height($(window).height() - tree.offset().top - 60)

        log('TREE:OK')
    } catch (e) {
        log('TREE:FAIL');
        log(e.message)
    }

    pktb.getColumnsArray = function(table){
        var tableHeaders = jQuery(table).find("thead th");
        var columnsArray = new Array();
        for(var numOfHeader = 0; numOfHeader < tableHeaders.length; numOfHeader++){
            columnsArray.push({'bSortable': false});//sWidth : '100px'});
        }
        return columnsArray;
    };

    // datatables на таблицы отчетов
    pktb.bind('ajaxStop', function () {
        $.unblockUI()
        commons();
        var tables = new Array();
        pktb.find('.report .body table:not(.params):not(.dataTable)').each(function(e){
            tables.push(jQuery(this));
            var columns = pktb.getColumnsArray(jQuery(this));
            jQuery(this).css('table-layout', 'auto').dataTable({
                bJQueryUI:true,
                //aoColumns : columns,
                bSort: false,
                oLanguage:{
                    sProcessing:'Подождите...',
                    sLengthMenu:'Показать _MENU_ записей',
                    sZeroRecords:'Нет записей',
                    sInfo:'Показаны с _START_ до _END_ из _TOTAL_ записей',
                    sInfoEmpty:'Записи с 0 до 0 из 0 записей',
                    sInfoFiltered:'(отфильтровано из _MAX_ записей)',
                    sInfoPostFix:'',
                    sSearch:'Поиск:',
                    sUrl:'',
                    oPaginate:{
                        sFirst:'В начало',
                        sPrevious:'Назад',
                        sNext:'Вперед',
                        sLast:'В конец'
                    }
                },
                sPaginationType:'full_numbers',
                sScrollX: '100%',
                bScrollCollapse: true,
                sDom:'<"H"lfr>t<"F"ip>',  
								fnRowCallback: function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {									
									$('td', nRow).each(function(i) {
										var $this = $(this)
										$this.html(aData[i].replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/\u0026amp;nbsp;/g, ' '))
									})
								}
            });
        });
    });

    var createReportFolder = pktb.find('.create-report-folder')
        .submit(function () {
            var $this = $(this);
            $.post(
                cr + '/reports/create.action', $this.serialize(),
                function (id, status) {
                    tree.jstree('refresh');
                    createReportFolder
                        .find('.where').text('Корневая папка')
                        .find('[name="parentReportId"]:hidden').val(0)
                }
            );
            return false;
        });
    pktb.statistics = function (data) {
        $.post(
            cr + '/reports/statistics.action',
            {
                id:data.id
            },
            function (data, status) {
                if (status == 'success')
                    alert("statistics request success");
            }
        );
    };

    pktb.getFiltersHTMLUnavailable = function () {
        pktb.find('.report .filter').empty().append(jQuery('<div>').addClass('filters-unavailable-message').html(FILTERS_UNAVAILABLE_MESSAGE));
    };

    pktb.getFiltersHTML = function (id) {
        //pktb.find('.report .filter').load(cr + '/reports/getFiltersHTML.action', {id:id});
        jQuery.ajax({
            //async:false,
            type:'POST',
            dataType:"html",
            url:cr + '/reports/getFiltersHTML.action',
            "data":{
                report:JSON.stringify({id:id})
            },
            beforeSend: function() {
                pktb.find('.report .filter')
                    .empty()
                    .prevAll('.preloader')
                    .remove()
                    .end()
                    .before('<div class="preloader" style="text-align: center"><img src="' + cr + '/img/preloader.gif" alt="Пожалуйста, подождите..."/> <span>Пожалуйста, подождите, идет загрузка...</span></div>')
            },
            success:function (r) {
                if (!r.status) {
                    jQuery(pktb.find('.report .filter')[0]).empty().append(r);
                    jQuery(pktb.find('.report .filter')[0]).hide();
                    pktb.getFilterDictionaries(null);
                    pktb.initFilterRelations();
                    jQuery(pktb.find('.report .filter')[0]).show();

                    pktb.find("button.pktb-reportPlaceholderSubmit")
                        .button()
                        .bind("click", {reportId:id}, function (e) {
							if (pktb.validateFiltersValues()) {
								if(typeof(jQuery("#pktb_project_requestOptions").attr("datasource")) != "undefined"){
									pktb.getReportFromDB(e.data);
								} else {
									pktb.getReport(e.data);
								}
							}
                        });

                    pktb.find('.clearReportBody:not(.ui-button)')
                        .button({disabled: true})
                        .click(function(){
                            $(this).button('option', 'disabled', true);
                            pktb.find('.report .body').children().hide().end().empty();
                        });

                    pktb.find('.getReportExcel')
                        .button()
                        .click(pktb.onGetReportExcelClick);

                    pktb.selectedReportIsFavourite = false;
                    $.each(pktb.favourites, function() {
                        if (this.id == pktb.selectedReportId )
                            pktb.selectedReportIsFavourite = true;
                    });
                    pktb.find('#addToFavourites')
                        .button({
                            icons: { primary: 'ui-icon-star' },
                            label: pktb.selectedReportIsFavourite
                                ? 'Удалить из избранного'
                                : 'Добавить в избранное'
                        })
                        .click(function() {
                            var $this = $(this)
                            $.getJSON(cr + '/reports/toggleReportByIdFavouriteToUserByCn.action', {
                                id: pktb.selectedReportId,
                                cn: cn
                            }, function(data) {
                                $this.button('option', 'label', data.favourited ? 'Удалить из избранного' : 'Добавить в избранное')
                                updateFavourites()
                            })
                        });

                    pktb.find('input.datePickerInput').datepicker({
                        showOn:'button',
                        buttonImage:cr + 'img/calendar.png',
                        buttonImageOnly:true
                    });
                    pktb.find('input.datePickerInput_simple').datepicker({
                        showOn:'button',
                        buttonImage:cr + 'img/calendar.png',
                        buttonImageOnly:true
                    });
                    pktb.find('input.datePickerInput_withTime').datetimepicker({
                        showOn:'button',
                        buttonImage:cr + 'img/calendar.png',
                        buttonImageOnly:true
                    });
                    pktb.find('input.timePickerInput').timepicker({
                        showOn:'button',
                        buttonImage:cr + 'img/calendar.png',
                        buttonImageOnly:true
                    });
                    pktb.find("input.datePickerInput[setCurrentTime=setCurrentTime]").datepicker("setDate", new Date());
                    pktb.find("input.datePickerInput_withTime[setCurrentTime=setCurrentTime]").datepicker("setDate", new Date());
                    pktb.find("input[setUsersRW=true]").val(currentUser.railway.code);
                }
            }
        });
    };
	
	
	
	pktb.validateFiltersValues = function(){
        var validated = true;
        var errors = new Array();
        jQuery(".filterItem[validation]").each(function(){
            if(jQuery(this).attr("validation") != ""){
                var validationOptions = JSON.parse(jQuery(this).attr("validation"));
                switch (validationOptions.type){
                    case "NO_TEXT" : var valid = pktb.validateNoTextInputs(jQuery(this).find("input"));
                        if(!valid){
                            validated = false;
                            errors.push("Не указан параметр \"" + jQuery(this).find("label").html() + "\"");
                        }
                        break;
                }
            }
        });
        if(!validated){
            pktb.displayErrors(errors);
        }
        return validated;
    };

    pktb.displayErrors = function(errors){
        var dialogElem = jQuery("<div title='Ошибки запроса'></div>");
        if(typeof(errors) != "undefined"){
            for(var numOfError = 0; numOfError < errors.length; numOfError++){
                jQuery(dialogElem).append("<div>" + errors[numOfError] + "</div>");
            }
        }
        dialogElem.dialog({
                        resizable:false,
                        modal:true,
                        buttons:{
                            'Закрыть':function () {
                                dialogElem.dialog('close').remove();
                            }
                        }
                    });
    };

    pktb.validateNoTextInputs = function(inputElem){
        if(typeof(inputElem) != "undefined"){
            if(typeof(jQuery(inputElem).val()) == "undefined" || jQuery(inputElem).val() == ""){
                return false;
            }
        }
        return true;
    };

	
	
	

    pktb.initFilterRelations = function(){
        pktb.initSwitchingFilters();
        pktb.initChangingKeyFilters();
    };

    pktb.initSwitchingFilters = function () {
        jQuery(".filterItem[switchType]").each(function () {
            if (jQuery(this).attr("switchtype") == "NO_INPUT_TEXT") {
                jQuery(".filterItem[paramkey=" + jQuery(this).attr("switchrelativefilter") + "] input").bind("keypress", {filterKeyToSwitch:jQuery(this)}, function (e) {//.attr("paramkey")
                    if (typeof(jQuery(this).val()) == "undefined" || jQuery(this).val() == "") {
                        jQuery(e.data.filterKeyToSwitch).show();
                    } else {
                        jQuery(e.data.filterKeyToSwitch).hide();
                    }
                });
            } else if (jQuery(this).attr("switchtype") == "SELECT") {
                jQuery(".filterItem[paramkey=" + jQuery(this).attr("switchrelativefilter") + "] select").bind("change", {filterKeyToSwitch:jQuery(this)}, function (e) {//.attr("paramkey")
                    var switchParams = JSON.parse(jQuery(e.data.filterKeyToSwitch).attr("switchparams").trim());
                    if (typeof(jQuery(this).val()) != "undefined" && jQuery(this).val() != "") {
                        var optionValue = jQuery(this).find("option:selected").attr("value");
                        if (typeof(switchParams.includeValues) != "undefined" && switchParams.includeValues.length > 0) {
                            var rest = $.grep(switchParams.includeValues, function (item) {
                                return (item == optionValue);
                            });
                            if (typeof(rest) != "undefined" && rest != null && rest.length > 0) {
                                jQuery(e.data.filterKeyToSwitch).show();
                            } else {
                                jQuery(e.data.filterKeyToSwitch).hide();
                            }
                        } else if(typeof(switchParams.excludeValues) != "undefined" && switchParams.excludeValues.length > 0){
                            var rest = $.grep(switchParams.excludeValues, function (item) {
                                return (item == optionValue);
                            });
                            if (typeof(rest) != "undefined" && rest != null && rest.length > 0) {
                                jQuery(e.data.filterKeyToSwitch).hide();
                            } else {
                                jQuery(e.data.filterKeyToSwitch).show();
                            }
                        }
                    } else {
                        jQuery(e.data.filterKeyToSwitch).hide();
                    }
                }).change();
            }
        });
    };

    pktb.initChangingKeyFilters = function () {
        jQuery(".filterItem[changekeyoptions]").each(function () {
            if (typeof(jQuery(this).attr("relativefilterkey")) != "undefined") {
                jQuery(".filterItem[paramkey=\"" + jQuery(this).attr("relativefilterkey") + "\"] select").bind("change", {filterKeyToChange:jQuery(this), changekeyoptions : JSON.parse(jQuery(this).attr("changekeyoptions"))}, function (e) {//.attr("paramkey")

                    var filterValue = jQuery(this).val();
                    var rest = $.grep(e.data.changekeyoptions, function(item) {
                        return (item["relativeFilterValue"] == filterValue);
                    });
                    jQuery(e.data.filterKeyToChange).attr("paramkey", rest[0].keyToSwitch);
                });
            }
        });
    };

    pktb.getFilterDictionaries = function (data) {
        var filterSelects = jQuery(".filterItem select[serviceuid]");
        if (typeof(filterSelects) != "undefined" && filterSelects.length > 0) {
            pktb.getNextDictionary(filterSelects, 0);
        }else {
            pktb.find('.report .filter').prev('.preloader').remove();
        }

    };

    pktb.getNextDictionary = function (selects, numToShow) {
        var uid = jQuery(selects[numToShow]).attr("serviceuid");
        jQuery.ajax({
            async:false,
            type:'POST',
            dataType:"json",
            url:cr + '/reports/getFilterDictionary.action',
            "data":{
                requestBean:JSON.stringify({filterUID:jQuery(selects[numToShow]).attr("serviceuid"), userBean:{userId:currentUser.userId}})
            },
            success:function (r) {
                if (!r.status) {
                    pktb.addDictionaryItems(r, selects[numToShow], uid);
                    numToShow++;
                    if (numToShow < selects.length) {
                        pktb.getNextDictionary(selects, numToShow);
                    } else {
                        pktb.find('.report .filter').prev('.preloader').remove();
                    }
                    //alert("answer is: " + r);
                    //corpUniverGoalsAddComment();
                    //                            displayRecomendations(r);
                    //                            jQuery("#goal_add_comment_window").dialog("close");
                }
            },
            error:function (jqXHR, textStatus, errorThrown) {
                numToShow++;
                if (numToShow < selects.length) {
                    pktb.getNextDictionary(selects, numToShow);
                }
            }
        });
    };

    pktb.insertStatisticsFilterInContainer = function (container, data) {
        var statisticsButton = jQuery('<button>').text('Запросить статистику').addClass('statistics').button().bind('click', function () {
            pktb.insertStatisticsInContainer(pktb.find('.report .body'), data);
        });
        container.empty().append(statisticsButton);
    };

    pktb.addButttonToTD = function (row, rightsId) {
        row.append(jQuery('<td>').append(jQuery('<span>').addClass('ui-icon').addClass('ui-icon-closethick').bind('click', {row:row, id:rightsId}, function (e) {
            $.post(
                cr + '/reports/removeRights.action',
                {
                    id:e.data.id
                },
                function (data, status) {
                    if (status == 'success') {
                        jQuery(e.data.row).remove();
                    }
                }
            );
        })));
        return row;
    };

    pktb.addRadios = function (data) {
        var inputAutocomplete = pktb.find('input.autocompleteUsers')
        var radioRights = pktb.find('div.radioRights');
        if (reportRightsBeans.length == 0) {
            radioRights.find("#radio1").attr('checked', 'checked');
            inputAutocomplete.attr('disabled', 'disabled');
        } else {
            radioRights.find("#radio2").attr('checked', 'checked');
        }
        radioRights.find("#radio1").bind('change', function () {
            if (confirm("Вы уверены что хотите разрешить доступ для всех?")) {
                inputAutocomplete.attr('disabled', 'disabled');
                $.post(
                    cr + '/reports/setAvailableForAll.action',
                    data,
                    function (dataRet, status) {
                        if (status)
                            tree.jstree("refresh");
                    }
                );
            }
        });
        radioRights.find("#radio2").bind('change', function () {
            inputAutocomplete.removeAttr('disabled');
        });
        radioRights.buttonset();
    };

    pktb.insertRightsFilterInContainer = function (container, data) {
        container.empty().load(cr + 'reports/rightsFilter.action', data, function () {
            var tableUsers = container.find('table.users tbody');
            var groupsUsers = container.find('table.groups tbody');
            var inputAutocomplete = pktb.find('input.autocompleteUsers').autocomplete({
                minLength:4,
                source:function (request, response) {
                    pktb.findUserPD(request, response);
                },
                select:function (event, ui) {
                    pktb.addUserPD(ui.item.allData, tableUsers, groupsUsers, data);
                }
            });
            pktb.find('.report .refresh').button({
                icons: {
                    primary: "ui-icon-arrowrefresh-1-w"
                },
                text: false
            }).bind('click',function(){
                    tree.jstree('refresh');
                });
            pktb.addRadios(data);
            for (var reportRightsItr = 0; reportRightsItr < reportRightsBeans.length; reportRightsItr++) {
                if (reportRightsBeans[reportRightsItr].user != null) {
                    var row = pktb.createUserRow(reportRightsBeans[reportRightsItr].user);
                    tableUsers.append(pktb.addButttonToTD(row, reportRightsBeans[reportRightsItr].rightsId));
                } else if (reportRightsBeans[reportRightsItr].userGroup != null) {
                    var row = pktb.createGroupRow(reportRightsBeans[reportRightsItr].userGroup);
                    groupsUsers.append(pktb.addButttonToTD(row, reportRightsBeans[reportRightsItr].rightsId));
                }
            }
        });
    };

    pktb.findUserPD = function (request, response) {
        jQuery.ajax({
            async:true,
            type:'POST',
            url:cr + 'reports/autocompleteUserBeans.action',
            data:{
                term:request.term
            },
            success:function (data) {
                var users = $.map(data.userBeans, function (item) {
                    return {
                        label:item.displayName,
                        value:item.displayName,
                        allData:item
                    }
                });
                var userGroups = $.map(data.userGroupBeans, function (item) {
                    return {
                        label:item.name,
                        value:item.name,
                        allData:item
                    }
                });
                response(users.concat(userGroups));
            }
        });
    };

    pktb.createUserRow = function (user) {
        var row = jQuery('<tr>').append(jQuery('<td>'));
        row.find('td').append(jQuery('<span>').html(user.displayName));
        return row;
    };

    pktb.createGroupRow = function (group) {
        var row = jQuery('<tr>').append(jQuery('<td>'));
        row.find('td').append(jQuery('<span>').html(group.name));
        return row;
    };

    pktb.addRightsAjax = function (data, userOrGroupData, tableUsers, callback) {
        $.post(
            cr + '/reports/addRights.action',
            data,
            callback
        );
    };

    pktb.addUserPD = function (userOrGroupData, tableUsers, tableGroupsUsers, reportData) {
        if (userOrGroupData != null && userOrGroupData.userId != null) {
            var data = {
                reportRightsBean:JSON.stringify(
                    {
                        user:{userId:userOrGroupData.userId},
                        report:{id:reportData.id}
                    }
                )
            };
            var callback = function (rigths, status) {
                if (status && !rigths.isExist) {
                    var row = pktb.createUserRow(userOrGroupData);
                    tableUsers.append(pktb.addButttonToTD(row, rigths.rightsId));
                } else {
                    log('addRigths:FAIL');
                }
            };
            pktb.addRightsAjax(data, userOrGroupData, tableUsers, callback);
        } else if (userOrGroupData != null && userOrGroupData.id != null) {
            var data = {
                reportRightsBean:JSON.stringify(
                    {
                        userGroup:{id:userOrGroupData.id},
                        report:{id:reportData.id}
                    }
                )
            };
            var callback = function (rigths, status) {
                if (status && !rigths.isExist) {
                    var row = pktb.createGroupRow(userOrGroupData);
                    tableGroupsUsers.append(pktb.addButttonToTD(row, rigths.rightsId));
                } else {
                    log('addRigths:FAIL');
                }
            };
            pktb.addRightsAjax(data, userOrGroupData, tableUsers, callback);
        }
    };

    pktb.extractStatData = function (status, data, container, isGlobal) {
        if (status) {

            var statisticsTable = jQuery('<table class="statisticsReports">').append(
                jQuery('<thead>').append(jQuery('<tr>')
                    .append(jQuery('<th>').text('Наименование отчета'))
                    .append(jQuery('<th>').text('Дата запроса'))
                    .append(jQuery('<th>').text('Пользователь'))
                    .append(jQuery('<th>').text('Результат'))
                )).append(
                jQuery('<tbody>')
            );
            if (!isGlobal) {
                statisticsTable.find('thead tr')
                    .append(jQuery('<th>').text('Передано байт'))
                    .append(jQuery('<th>').text('Получено байт'));
            }
            for (var headerIterator = 0; headerIterator < data.length; headerIterator++) {
                var row = jQuery("<tr>")
                    .append(jQuery('<td>').text(data[headerIterator].reportName))
                    .append(jQuery('<td>').text(data[headerIterator].dateOfDownload))
                    .append(jQuery('<td>').text(data[headerIterator].userDisplayName))
                    .append(jQuery('<td>').text(data[headerIterator].success));
                if (!isGlobal) {
                    row.append(jQuery('<td>').text(data[headerIterator].requestSize))
                        .append(jQuery('<td>').text(data[headerIterator].responseSize));
                }
                statisticsTable.find('tbody').append(row);
            }
            container.empty().append(statisticsTable);
        } else {
            container.empty().html("статистики нет по данному отчету");
        }
    };

    pktb.insertStatisticsInContainer = function (container, data) {
        $.post(
            cr + '/reports/statistics.action',
            {
                id:data.id
            },
            function (data, status) {
                pktb.extractStatData(status, data, container, false);
            }
        );
    };

    pktb.insertRightsInContainer = function (container, data) {
        container.empty().html("Доступен всем.");
    };

    pktb.addDictionaryItems = function (data, select, uid) {
        if (typeof(data.pairList) != "undefined") {
            //var selectElem = pktb.find(".filterItem select[serviceuid=" + uid + "]")[0];
            //var setUsersDefault = false;
            //if(jQuery(select).is("[setUsersRW=true]")){
            //	setUsersDefault = true;
            //jQuery(select).find("option[value=" + currentUser.railway.code + "]").attr("checked", "checked");
            //}
            for (var numOfPair = 0; numOfPair < data.pairList.length; numOfPair++) {
                //var checkedStr = "";
                //if(setUsersDefault && data.pairList[numOfPair].value == currentUser.railway.code){
                //	checkedStr = "checked";
                //}
                jQuery(select).append("<option value='" + data.pairList[numOfPair].name + "'>" + data.pairList[numOfPair].value + "</option>");
            }
            if(jQuery(select).is("[setUsersRW=true]")){
                jQuery(select).find("option[value=" + currentUser.railway.code + "]").attr("selected", "selected");
            }
        }
        /*if(jQuery(select).is("[setUsersRW=true]")){
         jQuery(select).find("option[value=" + currentUser.railway.code + "]").attr("checked", "checked");
         }*/
    };


    pktb.getReport = function (data) {
        var requestBean = {reportId:data.reportId, userBean:{userId:currentUser.userId}, datasource : jQuery("#pktb_project_requestOptions").attr("datasource"),  proc_name : jQuery("#pktb_project_requestOptions").attr("procedureName")}; //datasource    proc_name
        var pairList = new Array();
        var headerList = new Array();
        if (typeof(data.requestParams) != "undefined") {
            pairList = pktb.getRequestPairsFromString(data.requestParams);
        } else {
            pairList = pktb.getReportRequestPairs();
        }
        requestBean.pairList = pairList;
//        pktb.find('.report .body').load(cr + 'reports/getReport.action', {requestBean:JSON.stringify(requestBean)});

        jQuery.ajax({
            type:'POST',
            dataType:"html",
            url:cr + '/reports/getReport.action',
            "data":{
                requestBean:JSON.stringify(requestBean)
            },
            beforeSend: function() {
                pktb.find('.report .body').empty()//.append('<div style="text-align: center"><img src="' + cr + '/img/preloader.gif" alt="Пожалуйста, подождите..."/> <span>Пожалуйста, подождите, идет загрузка...</span></div>')
                $.blockUI({
                    css: {
                        border: 'none',
                        padding: '15px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: .5,
                        color: '#fff'
                    },
                    message: '<img src="' + cr + '/img/loader.gif"><div style="color:#ffffff;font-family:Tahoma, sans-serif">Загрузка отчета</div>'
                });
            },
            success:function (r, status) {
                r = JSON.parse(r);
                if (r.success) {
                    jQuery(pktb.find('.report .body')).empty().append(r.response);
                   /*( if(requestBean.reportId == 419){
                        jQuery(pktb.find('.report table:not(.params)')).addClass("params").each(function(){
                            var $this = $(this)
                            $this
                               .addClass('dataTable display')
                               .css('width', '100%')
                               .css('border-collapse', 'collapse')
                               .find('th')
                                    .addClass('ui-state-default')
                                    .wrapInner('<div class="DataTables_sort_wrapper"/>')
                            $this.find('tr').filter(':odd').addClass('odd').end().filter(':even').addClass('even')
                        });
                    } */
                    pktb.find("button.pktb-reportPlaceholderSubmit").button();
                    pktb.find(".report .body .innerReportLink").bind("click", {reportId:data.reportId, requestParams : jQuery(this).attr("requestparams")}, function (e) {
                        e.data.requestParams = jQuery(this).attr("requestparams");
                        pktb.getReport(e.data);
                    });

                } else {
                    var e = r.response,
                        container = $('<div>', {'class': 'error'}),
                    //headerText = 'Ошибка сервиса: ' + e.slice(0, e.indexOf('\n')),
                    //stackTraceText = e.slice(e.indexOf('\n')),
                    //header = $('<a>', {'href': '#', text: headerText, 'class': 'header'}),
                    //stackTrace = $('<div>',{text: stackTraceText, 'class': 'stackTrace'}),
                        header= $('<span>', {
                            text: 'Произошла ошибка, ',
                            'class': 'header'
                        }),
                        more = $('<a>', {
                            'href': '#',
                            text: 'подробнее...'
                        }),
                        stackTrace = $('<pre>', {
                            text: r.response,
                            'class': 'stackTrace'
                        });

                    header.append(more);
                    container.append(header).append(stackTrace)

                    stackTrace.hide();

                    //container.append(header).append(stackTrace)
                    //stackTrace.hide();
                    pktb.find('.report .body').empty().append(container)

                    more.click(function(){ stackTrace.slideToggle() })

                }
                $.unblockUI()
                pktb.find('.clearReportBody').button('option', 'disabled', false);


            }
        });

    };


    pktb.getReportFromDB = function(data){
        var requestBean = {reportId:data.reportId, userBean:{userId:currentUser.userId}, datasource : jQuery("#pktb_project_requestOptions").attr("datasource"),  proc_name : jQuery("#pktb_project_requestOptions").attr("procedureName")}; //datasource    proc_name
        var pairList = new Array();
        var tableBeans = new Array();
        var dictionaries = new Array();
        var importedDictionaries = new Array();
        if (typeof(data.requestParams) != "undefined") {
            pairList = pktb.getRequestPairsFromString(data.requestParams);
        } else {
            pairList = pktb.getReportRequestPairs();
            try{
                tableBeans = pktb.getReportHeaders();
            } catch (e){
                log('getReportHeaders:FAIL');
                log(e.message);
            }
            try{
                dictionaries = pktb.getDictionaries();
            } catch (e){
                log('getdictionaries:FAIL');
                log(e.message);
            }
            try{
                importedDictionaries = pktb.importDictionaries();
            } catch (e){
                log('importDictionaries:FAIL');
                log(e.message);
            }
        }
        requestBean.pairList = pairList;
        requestBean.tableBeans = tableBeans;
        requestBean.dictionaries = dictionaries;
        requestBean.importedDictionaries = importedDictionaries;
        jQuery.ajax({
            type:'POST',
            dataType:"html",
            url:cr + '/reports/getReportFromDB.action',
            "data":{
                requestBean:JSON.stringify(requestBean)
            },
            beforeSend: function() {
                pktb.find('.report .body').empty()//.append('<div style="text-align: center"><img src="' + cr + '/img/preloader.gif" alt="Пожалуйста, подождите..."/> <span>Пожалуйста, подождите, идет загрузка...</span></div>')
                $.blockUI({
                    css: {
                        border: 'none',
                        padding: '15px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: .5,
                        color: '#fff'
                    },
                    message: '<img src="' + cr + '/img/loader.gif"><div style="color:#ffffff;font-family:Tahoma, sans-serif">Загрузка отчета</div>'
                });
            },
            success:function (r, status) {

                r = JSON.parse(r);
                if (r.success) {
                    jQuery(pktb.find('.report .body')).empty().append(r.response);

                    pktb.find("button.pktb-reportPlaceholderSubmit").button();
                    pktb.find(".report .body .innerReportLink").bind("click", {reportId:data.reportId, requestParams : jQuery(this).attr("requestparams")}, function (e) {
                        e.data.requestParams = jQuery(this).attr("requestparams");
                        pktb.getReport(e.data);
                    });
                } else {
                    var e = r.response,
                        container = $('<div>', {'class': 'error'}),
                        header= $('<span>', {
                            text: 'Произошла ошибка, ',
                            'class': 'header'
                        }),
                        more = $('<a>', {
                            'href': '#',
                            text: 'подробнее...'
                        }),
                        stackTrace = $('<pre>', {
                            text: r.response,
                            'class': 'stackTrace'
                        });

                    header.append(more);
                    container.append(header).append(stackTrace);

                    stackTrace.hide();
                    pktb.find('.report .body').empty().append(container);

                    more.click(function(){ stackTrace.slideToggle() })

                }
                pktb.find('.clearReportBody').button('option', 'disabled', false);


            }
        });
    };


    // Экспорт в Excel
    pktb.onGetReportExcelClick = function() {

        var data = {reportId: pktb.selectedReportId };

        var requestBean = {reportId:data.reportId, userBean:{userId:currentUser.userId}};
        var pairList = new Array();
        if (typeof(data.requestParams) != "undefined") {
            pairList = pktb.getRequestPairsFromString(data.requestParams);
        } else {
            pairList = pktb.getReportRequestPairs();
        }

        requestBean.pairList = pairList;
        window.location = cr + '/reports/getReportExcel.action?requestBean=' + encodeURIComponent(JSON.stringify(requestBean));
        return false;
    };



    pktb.getReportRequestPairs = function () {
        log('getReportRequestPairs');
        var pairList = new Array();
        jQuery(".filterItem select").each(function (el) {
            var container = jQuery(this).closest(".filterItem");

            function extractParameter(domElement, container) {
                var parameter = {name: jQuery(container).attr("paramkey"), value: jQuery(domElement).val()};
                if (typeof(jQuery(container).attr("orderNum")) != "undefined" && jQuery(container).attr("orderNum") != "") {
                    parameter.orderNum = jQuery(container).attr("orderNum");
                }
                if (typeof(jQuery(container).attr("markerSQLCode")) != "undefined" && jQuery(container).attr("markerSQLCode") != "") {
                    parameter.markerSQLCode = jQuery(container).attr("markerSQLCode");
                }
                if (typeof(jQuery(container).attr("paramType")) != "undefined" && jQuery(container).attr("paramType") != "") {
                    parameter.inputParam = jQuery(container).attr("paramType");
                }
                if (typeof(jQuery(container).attr("paramDataType")) != "undefined" && jQuery(container).attr("paramDataType") != "") {
                    parameter.paramDataType = jQuery(container).attr("paramDataType");
                }
                return parameter;
            }


            if (typeof(jQuery(this).find(":selected").attr("parammap")) != "undefined" && jQuery(this).find(":selected").attr("parammap") != ""){
                var selectParams = JSON.parse(jQuery(this).find(":selected").attr("parammap"));
                for (var paramIterator = 0; paramIterator < selectParams.length; paramIterator++){
                    var parameter = extractParameter(this, container);
                    parameter.value = selectParams[paramIterator].paramValue;
                    parameter.orderNum = new Number(parameter.orderNum)+paramIterator;
                    parameter.name = selectParams[paramIterator].key;
                    pairList.push(parameter);
                }
            } else {
                var parameter = extractParameter(this, container);
                pairList.push(parameter);
            }
            //pairList.push({name:jQuery(container).attr("paramkey"), orderNum : jQuery(container).attr("orderNum"), inputParam : jQuery(container).attr("paramType"), paramDataType : jQuery(container).attr("paramDataType")});
        });
        jQuery(".filterItem input").each(function (el) {
            var container = jQuery(this).closest(".filterItem");
            var parameter = {name:jQuery(container).attr("paramkey")};
            if(typeof(jQuery(container).attr("orderNum")) != "undefined" && jQuery(container).attr("orderNum") != ""){
                parameter.orderNum = jQuery(container).attr("orderNum");
            }
            if(typeof(jQuery(container).attr("markerSQLCode")) != "undefined" && jQuery(container).attr("markerSQLCode") != ""){
                parameter.markerSQLCode = jQuery(container).attr("markerSQLCode");
            }
            if(typeof(jQuery(container).attr("paramType")) != "undefined" && jQuery(container).attr("paramType") != ""){
                parameter.inputParam = jQuery(container).attr("paramType");
            }
            if(typeof(jQuery(container).attr("paramDataType")) != "undefined" && jQuery(container).attr("paramDataType") != ""){
                parameter.paramDataType = jQuery(container).attr("paramDataType");
            }
            if (jQuery(this).is("[type=checkbox]")) {

                if (jQuery(this).is(":checked")) {
                    parameter.value = 1;
                    //pairList.push({name:jQuery(container).attr("paramkey"), value:1, orderNum : jQuery(container).attr("orderNum"), inputParam : jQuery(container).attr("paramType"), paramDataType : jQuery(container).attr("paramDataType")});
                } else {
                    parameter.value = 0;
                    //pairList.push({name:jQuery(container).attr("paramkey"), value:0, orderNum : jQuery(container).attr("orderNum"), inputParam : jQuery(container).attr("paramType"), paramDataType : jQuery(container).attr("paramDataType")});
                }
                pairList.push(parameter);
            } else if (jQuery(this).is(".datePickerInput_withTime") || jQuery(this).is(".datePickerInput") || jQuery(this).is(".timePickerInput")) {
                pktb.addDateParams(jQuery(this), pairList);
                //pairList.push({name:jQuery(container).attr("paramkey"), value:jQuery(this).val()});
            } else {
                parameter.value = jQuery(this).val();
                pairList.push(parameter);
                //pairList.push({name:jQuery(container).attr("paramkey"), value:jQuery(this).val(), orderNum : jQuery(container).attr("orderNum"), inputParam : jQuery(container).attr("paramType"), paramDataType : jQuery(container).attr("paramDataType")});
            }
        });
        jQuery(".HIDDEN input[type=hidden]").each(function (el) {
            var container = jQuery(this).closest(".HIDDEN");
            var parameter = {name:jQuery(container).attr("paramkey"), value:jQuery(this).val()};
            if(typeof(jQuery(container).attr("orderNum")) != "undefined" && jQuery(container).attr("orderNum") != ""){
                parameter.orderNum = jQuery(container).attr("orderNum");
            }
            if(typeof(jQuery(container).attr("markerSQLCode")) != "undefined" && jQuery(container).attr("markerSQLCode") != ""){
                parameter.markerSQLCode = jQuery(container).attr("markerSQLCode");
            }
            if(typeof(jQuery(container).attr("paramType")) != "undefined" && jQuery(container).attr("paramType") != ""){
                parameter.inputParam = jQuery(container).attr("paramType");
            }
            if(typeof(jQuery(container).attr("paramDataType")) != "undefined" && jQuery(container).attr("paramDataType") != ""){
                parameter.paramDataType = jQuery(container).attr("paramDataType");
            }
            pairList.push(parameter);

        });
        return pairList;
    };

    pktb.getReportHeaders = function () {
        log('getReportHeaders');
        var tableBeans;
        var resultTablesInput = pktb.find(".resultTables").find('input');
        tableBeans = JSON.parse(jQuery(resultTablesInput).val()).tableBeans;
        return tableBeans;
    };

    pktb.getDictionaries = function () {
        log('dictionaries');
        var dictionaries;
        var dictionariesInput = pktb.find(".dictionaries").find('input');
        dictionaries = JSON.parse(jQuery(dictionariesInput).val()).dictionaries;
        return dictionaries;
    };

    pktb.importDictionaries = function () {
        log('importDictionaries');
        var importedDictionaries;
        var importedDictionariesInput = pktb.find(".importedDictionaries").find('input');
        importedDictionaries = JSON.parse(jQuery(importedDictionariesInput).val()).importedDictionaries;
        return importedDictionaries;
    };

    pktb.getRequestPairsFromString = function (requestString) {
        var jsonPairsList = new Array();
        var pairs = requestString.split("&");  //TODO переделать на регулярные выражения
        if (typeof (pairs) != "undefined" && pairs != null) {
            for (var numOfPair = 0; numOfPair < pairs.length; numOfPair++) {
                var paramNameValue = pairs[numOfPair].split("=");
                jsonPairsList.push({name:paramNameValue[0], value:paramNameValue[1]});
            }
        }
        return jsonPairsList;
    };

    pktb.addDateParams = function (inputElem, pairsArray) {
		//jQuery(inputElem).datetimepicker( "option", "dateFormat", "dd.MM.yyyy hh:mm");
        var selectedDate = jQuery(inputElem).datetimepicker( "getDate" );//.parseTime("hh:mm", jQuery(inputElem).val());//03.12.2012 17:57 //$.datepicker.parseTime("dd.MM.yyyy hh:mm", jQuery(inputElem).val());
        if(selectedDate == null && jQuery(inputElem).is(".timePickerInput")){
            selectedDate = $.datepicker.parseTime("hh:mm", jQuery(inputElem).val());
			if(selectedDate){
                selectedDate = new Date(0, 0, 0, selectedDate.hour, selectedDate.minute, 0, 0);
			}else{
				selectedDate = null;   
			}
        }

        if(selectedDate != null && (selectedDate.format("HH") == "00" || selectedDate.format("MM") == "00")){
        		var dateStr = jQuery(inputElem).val();
        		//dateStr.substring(dateStr.indexOf(":") - 2, dateStr.indexOf(":"))
        		//dateStr.substring(dateStr.indexOf(":") + 1, dateStr.indexOf(":") + 3)
        			selectedDate.setHours(dateStr.substring(dateStr.indexOf(":") - 2, dateStr.indexOf(":")), dateStr.substring(dateStr.indexOf(":") + 1, dateStr.indexOf(":") + 3), 00, 00);
        		}

        var container = jQuery(inputElem).closest(".filterItem");
        /*var pairObject = new Object();
         if (typeof(jQuery(container).attr("orderNum")) != "undefined" && jQuery(container).attr("orderNum") != "") {
         pairObject.orderNum = jQuery(container).attr("orderNum");
         }
         if (typeof(jQuery(container).attr("paramType")) != "undefined" && jQuery(container).attr("paramType") != "") {
         pairObject.inputParam = jQuery(container).attr("paramType");
         }
         if (typeof(jQuery(container).attr("paramDataType")) != "undefined" && jQuery(container).attr("paramDataType") != "") {
         pairObject.paramDataType = jQuery(container).attr("paramDataType");
         }
         if(typeof(jQuery(inputElem).val()) == "undefined" || jQuery(inputElem).val() == ""){
         pairObject.value = jQuery(inputElem).attr("defaultvalue");
         }*/
        //var selectedTime = jQuery(inputElem).datetimepicker('getTime');
        if (typeof(selectedDate) != "undefined" && selectedDate != null) {
            if (typeof(jQuery(inputElem).attr("param_date")) != "undefined") {
                var pairObject = {name : jQuery(inputElem).attr("param_date")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                if(typeof(jQuery(inputElem).attr("dateFormat")) != "undefined"){
                    pairObject.value = selectedDate.format(jQuery(inputElem).attr("dateFormat"));
                    //pairsArray.push({name:jQuery(inputElem).attr("param_date"), value:selectedDate.format(jQuery(inputElem).attr("dateFormat"))});
                } else {
                    pairObject.value = selectedDate.format("yyyy-mm-dd");
//                    pairsArray.push({name:jQuery(inputElem).attr("param_date"), value:selectedDate.format("yyyy-mm-dd")});
                }
                pairsArray.push(pairObject);
                //pairsArray.push(pairObject);
            }
            if (typeof(jQuery(inputElem).attr("param_year")) != "undefined") {
                var pairObject = {name : jQuery(inputElem).attr("param_year")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                pairObject.value = selectedDate.format("yyyy");
                pairsArray.push(pairObject);
                //pairsArray.push({name:jQuery(inputElem).attr("param_year"), value:selectedDate.format("yyyy")});
            }
            if (typeof(jQuery(inputElem).attr("param_month")) != "undefined") {
                var pairObject = {name : jQuery(inputElem).attr("param_month")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                pairObject.value = selectedDate.format("mm");
                pairsArray.push(pairObject);
                //pairsArray.push({name:jQuery(inputElem).attr("param_month"), value:selectedDate.format("mm")});
            }
            if (typeof(jQuery(inputElem).attr("param_day")) != "undefined") {
                var pairObject = {name : jQuery(inputElem).attr("param_day")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                pairObject.value = selectedDate.format("dd");
                pairsArray.push(pairObject);
                //pairsArray.push({name:jQuery(inputElem).attr("param_day"), value:selectedDate.format("dd")});//$.datepicker.formatDate("dd", selectedDate, null)
            }
            if (typeof(jQuery(inputElem).attr("param_hour")) != "undefined") {
                var pairObject = {name : jQuery(inputElem).attr("param_hour")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                pairObject.value = selectedDate.format("HH");
                var dateStr = jQuery(inputElem).val();
                if(pairObject.value == "00"){
                    pairObject.value = dateStr.substring(dateStr.indexOf(":") - 2, dateStr.indexOf(":"))
                }
                pairsArray.push(pairObject);  
                //pairsArray.push({name:jQuery(inputElem).attr("param_hour"), value:selectedDate.format("HH")});//$.datepicker.formatTime("hh", selectedDate, null)
            }
            if (typeof(jQuery(inputElem).attr("param_minute")) != "undefined") {
                var pairObject = {name : jQuery(inputElem).attr("param_minute")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                pairObject.value = selectedDate.format("MM");
                var dateStr = jQuery(inputElem).val();
                                if(pairObject.value == "00"){
                                    pairObject.value = dateStr.substring(dateStr.indexOf(":") + 1, dateStr.indexOf(":") + 3)
                                }
                pairsArray.push(pairObject);
                //pairsArray.push({name:jQuery(inputElem).attr("param_minute"), value:selectedDate.format("MM")}); //$.datepicker.formatTime("mm", selectedDate, null)
            }

        } else{
            if (typeof(jQuery(inputElem).attr("param_date")) != "undefined") {
                var pairObject = {name:jQuery(inputElem).attr("param_date")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                if (typeof(jQuery(inputElem).attr("defaultvalue")) != "undefined") {
                    pairObject.value = jQuery(inputElem).attr("defaultvalue");
                    pairsArray.push(pairObject);
                }
                /*if (typeof(jQuery(inputElem).val()) != "undefined" && jQuery(inputElem).val() != "") {
                 if (typeof(jQuery(inputElem).attr("dateFormat")) != "undefined") {
                 pairObject.value = selectedDate.format(jQuery(inputElem).attr("dateFormat"));
                 } else {
                 pairObject.value = selectedDate.format("yyyy-mm-dd");
                 }
                 }
                 pairsArray.push(pairObject);*/
            }
            if (typeof(jQuery(inputElem).attr("param_year")) != "undefined") {
                var pairObject = {name:jQuery(inputElem).attr("param_year")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                if (typeof(jQuery(inputElem).attr("defaultvalue")) != "undefined") {
                    pairObject.value = jQuery(inputElem).attr("defaultvalue");
                    pairsArray.push(pairObject);
                }
            }
            if (typeof(jQuery(inputElem).attr("param_month")) != "undefined") {
                var pairObject = {name:jQuery(inputElem).attr("param_month")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                if (typeof(jQuery(inputElem).attr("defaultvalue")) != "undefined") {
                    pairObject.value = jQuery(inputElem).attr("defaultvalue");
                    pairsArray.push(pairObject);
                }
            }
            if (typeof(jQuery(inputElem).attr("param_day")) != "undefined") {
                var pairObject = {name:jQuery(inputElem).attr("param_day")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                if (typeof(jQuery(inputElem).attr("defaultvalue")) != "undefined") {
                    pairObject.value = jQuery(inputElem).attr("defaultvalue");
                    pairsArray.push(pairObject);
                }
            }
            if (typeof(jQuery(inputElem).attr("param_hour")) != "undefined") {
                var pairObject = {name:jQuery(inputElem).attr("param_hour")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                if (typeof(jQuery(inputElem).attr("defaultvalue")) != "undefined") {
                    pairObject.value = jQuery(inputElem).attr("defaultvalue");
                    pairsArray.push(pairObject);
                }
            }
            if (typeof(jQuery(inputElem).attr("param_minute")) != "undefined") {
                var pairObject = {name:jQuery(inputElem).attr("param_minute")};
                pktb.addSingleDatePair(inputElem, container, pairObject);
                if (typeof(jQuery(inputElem).attr("defaultvalue")) != "undefined") {
                    pairObject.value = jQuery(inputElem).attr("defaultvalue");
                    pairsArray.push(pairObject);
                }
            }
            //pairsArray.push(pairObject);
        }
    };

    pktb.addSingleDatePair = function(inputElem, container, pairObject){
        if (typeof(jQuery(container).attr("orderNum")) != "undefined" && jQuery(container).attr("orderNum") != "") {
            pairObject.orderNum = jQuery(container).attr("orderNum");
        }
        if (typeof(jQuery(container).attr("markerSQLCode")) != "undefined" && jQuery(container).attr("markerSQLCode") != "") {
            pairObject.markerSQLCode = jQuery(container).attr("markerSQLCode");
        }
        if (typeof(jQuery(container).attr("paramType")) != "undefined" && jQuery(container).attr("paramType") != "") {
            pairObject.inputParam = jQuery(container).attr("paramType");
        }
        if (typeof(jQuery(container).attr("paramDataType")) != "undefined" && jQuery(container).attr("paramDataType") != "") {
            pairObject.paramDataType = jQuery(container).attr("paramDataType");
        }
        if(typeof(jQuery(inputElem).val()) == "undefined" || jQuery(inputElem).val() == ""){
            pairObject.value = jQuery(inputElem).attr("defaultvalue");
        }
    };

    // Применить роль
    var applyRole = function () {
        var username = pktb.find('.username');
        pktb.find('.report .statistics').hide();
        pktb.find('.report .rights').hide();
        if (currentUser.userRoleBean.roleKey == 'admin') {
            pktb.find('.report').addClass('adminRights');
            username.html(currentUser.displayName + " | " + currentUser.railway.name + "(" + currentUser.railway.code + ")");
        } else if (currentUser.userRoleBean.roleKey == 'listener') {
            username.html(currentUser.displayName + " | " + currentUser.railway.name + "(" + currentUser.railway.code + ")");
            pktb.find('.report').addClass('listenerRights');
        } else if (currentUser.userRoleBean.roleKey == 'anonymous') {
            username.html(currentUser.displayName + " | " + DISPLAY_NAME_UNKNOWN + "(" + DISPLAY_NAME_UNKNOWN + ")");
            pktb.find('.report').addClass('anonymousRights');
            pktb.find('.report .body').append(jQuery('<div>').addClass('anonymous-rights-message').html(ANONYMOUS_RIGHTS_MESSAGE));
        }
    };
    try {
        applyRole();
        log('applyRole:OK')
    } catch (e) {
        log('applyRole:FAIL');
        log(e.message)
    }

    // Общая статистика
    try {
        var globalStatistics = pktb.find('.globalStatistics');
        globalStatistics.bind('click', function () {
            pktb.showGlobalStatisticsFilter(pktb.find('.report .filter').empty(), pktb.find('.report .body').empty());
        });
        pktb.showGlobalStatisticsFilter = function (filter, body) {
            filter.load(cr + 'jsp/statisticsFilter.jsp', function () {
                pktb.find('input.datePickerInput_withTime').datetimepicker({
                    showOn:'button',
                    buttonImage:cr + 'img/calendar.png',
                    buttonImageOnly:true
                });
                pktb.find('input.autocompleteReports').autocomplete({
                    minLength:4,
                    source:cr + 'reports/autocompleteReports.action'

                });
                pktb.find('input.autocompleteUsers').autocomplete({
                    minLength:4,
                    source:cr + 'reports/autocompleteUsers.action'

                });
                filter.find('.statisticsFilterSubmit').button().bind('click', function () {

                    pktb.showGlobalStatistics(filter, body);
                });
            });
        };
        pktb.showGlobalStatistics = function (filter, body) {
            var globalStatisticsRequest = new Object();
            if (jQuery.trim(filter.find('[paramkey=reportName] input').val()) != "")
                globalStatisticsRequest.reportName = filter.find('[paramkey=reportName] input').val();
            if (jQuery.trim(filter.find('[paramkey=dateOfDownloadBegin] input').val()) != "")
                globalStatisticsRequest.dateOfDownloadBegin = filter.find('[paramkey=dateOfDownloadBegin] input').val();
            if (jQuery.trim(filter.find('[paramkey=dateOfDownloadEnd] input').val()) != "")
                globalStatisticsRequest.dateOfDownloadEnd = filter.find('[paramkey=dateOfDownloadEnd] input').val();
            if (jQuery.trim(filter.find('[paramkey=userDisplayName] input').val()) != "")
                globalStatisticsRequest.userDisplayName = filter.find('[paramkey=userDisplayName] input').val();
            if (jQuery.trim(filter.find('[paramkey=success] select').val()) != "")
                globalStatisticsRequest.success = filter.find('[paramkey=success] select').val();
            $.post(
                cr + '/reports/globalStatistics.action',
                {
                    globalStatisticsRequest:JSON.stringify(globalStatisticsRequest)
                },
                function (data, status) {
                    if (status == 'success')
                        pktb.extractStatData(status, data, body, true);
                }
            );
        };
        log('globalStatistics.CONSOLE:OK');
    } catch (e) {
        log('globalStatistics.CONSOLE:FAIL');
        log(e.message);
    }

};

function pktb_project_callStatement(){
    jQuery.ajax({
        type:'POST',
        dataType:"html",
        url:"http://10.23.248.182:10046/lotus/PA_1_OK7OBFH200LJ40IJTIQ17V0000/" + '/reports/testCallableStatement.action',
        "data":{
//                            requestBean:JSON.stringify(requestBean)
        },
        success:function (r, status) {
        }
    });
}