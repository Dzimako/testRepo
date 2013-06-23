var pktbAdminConsole = function(vars) {

	var ns = vars.ns,
		cr = vars.cr,
		currentUser = vars.currentUser,
		userServiceURL = vars.userServiceURL

	var pktb = $('#' + ns + 'pktb'),
			ac = pktb.find('.adminConsole'),
			adminConsole = ac


	$('[data-jquery-ui]').each(function(){
		var $this = $(this),
				widgets = eval('({' + $this.attr('data-jquery-ui') + '})')
				
				for(widget in widgets)
					$this[widget](widgets[widget])
	})		
			
			
	ac
		.dialog({ 
			autoOpen:false,
			width:700,
			//height: 300,
            resizable: false
		})
		.css('height', 'auto')
		.parent()
			.addClass('pktb')

  pktb.find('.showAdminConsole')
    .click(function(){ ac.dialog('open').css('height', 'auto') })

    $('#railwayListGroupListRadioContainer :radio').change(function() {
        $('#railwayList, #groupList').toggle()
        $('#userList').empty()
        $('#railwayList, #groupList').children().removeClass('ui-state-active selectedRailway selectedGroup')
        $('#addRailwayButton, #addGroupButton').toggle()
        $('#addUserConfirmButton').button('option', 'disabled', true)
    })


	var railways = []
    var selectedRailway = undefined;

    var renderRailway = function(self) {

        var railwayLabel = $('<span>', {
            'class': 'railwayLabel',
            'html': '<span class="railwayCode">' + self.code + '</span><span class="railwayName">' + self.name + '</span>'
        })

        var removeRailway = $('<div>', {
            'html': '<span class="ui-icon ui-icon-trash"></span>',
            'class': 'removeRailway',
            'title': 'Удалить запись о дороге',
            'click': function() {
                pktbConfirm('Удалить запись о "' + self.name + ' (' + self.code + ')"?', function(){
                    $.getJSON(cr + '/adminConsole/deleteRailwayById.action', {id: self.id}, function(data){
                        if (!data.usersExist)
                            railway.remove()
                        else
                            pktbAlert('Запись о дороге не может быть удалена. Необходимо сперва удалить или переместить всех приписаных к ней пользователей.')
                    })
                })
                return false
            }
        })

        var editRailway = $('<div>', {
            'html': '<span class="ui-icon ui-icon-pencil"></span>',
            'class': 'editRailway',
            'title': 'Редактировать запись о дороге',
            'click': function() {
                railway.addClass('editing').find(':text:first').focus()
                return false
            }
        })

        var railwayEditor = $('<form>', {
            'action': '#',
            'class': 'railwayEditor',
            'html': '<input type="hidden" name="id" value="' + self.id + '"><input name="code" class="ui-corner-all" size="3" placeholder="Код" value="' + self.code + '"><input name="name" class="ui-corner-all" placeholder="Название дороги" size="30" value="' + self.name + '">'
        })

        railwayEditor.children().click(false)
        //railwayEditor.submit(false)
        railwayEditor.find(':text:last').keypress(function(e) {
            var code = (e.keyCode ? e.keyCode : e.which)
            if(code == 13) {
                $(this).blur()
                confirmEditRailway.trigger('click')
            }
        })

        var confirmEditRailway = $('<div>', {
            'html': '<span class="ui-icon ui-icon-check"></span>',
            'class': 'confirmEditRailway',
            'title': 'ОК',
            'click': function() {
                //for(f in self)
                //    self[f] = railwayEditor.find('input[name="' + f + '"]').val()

                $.getJSON( cr + '/adminConsole/saveOrUpdateRailway.action', railwayEditor.serialize(), function (data) {
                    for(f in data) {
                        self[f] = data[f]
                        railwayEditor.find('input[name="' + f + '"]').val(self[f])
                    }
                    railwayLabel.html('<span class="railwayCode">' + self.code + '</span><span class="railwayName">' + self.name + '</span>')
                    railway.removeClass('editing')
                })
                return false
            }
        })

        var cancelEditRailway = $('<div>', {
            'html': '<span class="ui-icon ui-icon-closethick"></span>',
            'class': 'cancelEditRailway',
            'title': 'Отмена',
            'click': function() {
                railway.removeClass('editing')

                for(f in self)
                    railwayEditor.find('input[name="' + f + '"]').val(self[f])

                if (!self.id)
                    railway.remove()

                return false
            }
        })

        var railway = $('<li>', {
            'class': 'railway ui-state-default ui-corner-all'
        })
        .click(function(){
            $(this).addClass('ui-state-active selectedRailway').siblings().removeClass('ui-state-active selectedRailway')
            selectedRailway = self
            selectedGroup = undefined
            loadUserListForRailway(self)
            $('#addUserDisplayNameInput').removeAttr('disabled')
        })
        .hover(function() { $(this).toggleClass('hoverRailway ui-state-hover')})
        .prepend(railwayLabel)
        .prepend(railwayEditor)
        .prepend(editRailway)
        .prepend(removeRailway)
        .prepend(cancelEditRailway)
        .prepend(confirmEditRailway)
        .droppable({
            accept: ".user",
            hoverClass: 'ui-state-hover',
            drop: function( event, ui ) {
                moveUserToRailway(selectedUser, self, function() {
                    $(event.target).effect('highlight')
                })

            }
        });

        return railway
    }

	var loadRailwayList = function() {
		$.getJSON( cr + '/adminConsole/getAllRailways.action', function (data) {

			$('#railwayList').empty()

			$.each(data, function () {
			    $('#railwayList').append(renderRailway(this));
			})
		})
	}

	loadRailwayList()

    var groups = []
    var selectedGroup = undefined;

    var renderGroup = function(self) {

        var groupLabel = $('<span>', {
            'class': 'groupLabel',
            'text': self.name
        })

        var groupRights = $('<div>', {
            'html': '<span class="ui-icon ui-icon-locked"></span>',
            'class': 'groupRights',
            'title': 'Права группы',
            'click': function() {
                openGroupRightsDialog(self)
                return false
            }
        })

        var removeGroup = $('<div>', {
            'html': '<span class="ui-icon ui-icon-trash"></span>',
            'class': 'removeGroup',
            'title': 'Удалить запись о группе',
            'click': function() {
                pktbConfirm('Удалить запись о группе "' + self.name + '"?', function(){
                    $.getJSON(cr + '/adminConsole/deleteGroupById.action', {id: self.id}, function(){
                        group.remove()
                    })
                })
                return false
            }
        })

        var editGroup = $('<div>', {
            'html': '<span class="ui-icon ui-icon-pencil"></span>',
            'class': 'editGroup',
            'title': 'Редактировать запись о группе',
            'click': function() {
                group.addClass('editing').find(':text:first').focus()
                return false
            }
        })

        var groupEditor = $('<form>', {
            'action': '#',
            'class': 'groupEditor',
            'html': '<input type="hidden" name="id" value="' + self.id + '"><input class="ui-corner-all" name="name" placeholder="Название группы" size="30" value="' + self.name + '">'
        })

        groupEditor.children().click(false)
        groupEditor.submit(function() { return false })
        groupEditor.find(':text:last').keypress(function(e) {
            var code = (e.keyCode ? e.keyCode : e.which)
            if(code == 13) {
                $(this).blur()
                confirmEditGroup.trigger('click')
            }
        })

        var confirmEditGroup = $('<div>', {
            'html': '<span class="ui-icon ui-icon-check"></span>',
            'class': 'confirmEditGroup',
            'title': 'ОК',
            'click': function() {
                //for(f in self)
                //    self[f] = groupEditor.find('input[name="' + f + '"]').val()

                $.getJSON( cr + '/adminConsole/saveOrUpdateGroup.action', groupEditor.serialize(), function (data) {
                    self = data
                    groupLabel.text(self.name)
                    for(f in self)
                        groupEditor.find('input[name="' + f + '"]').val(self[f])
                    group.removeClass('editing')
                })
                return false
            }
        })

        var cancelEditGroup = $('<div>', {
            'html': '<span class="ui-icon ui-icon-closethick"></span>',
            'class': 'cancelEditGroup',
            'title': 'Отмена',
            'click': function() {
                group.removeClass('editing')

                for(f in self)
                    groupEditor.find('input[name="' + f + '"]').val(self[f])

                if (!self.id)
                    group.remove()

                return false
            }
        })

        var group = $('<li>', {
            'class': 'group ui-state-default ui-corner-all'
        })
            .click(function(){
                $(this).addClass('ui-state-active selectedGroup').siblings().removeClass('ui-state-active selectedGroup')
                selectedRailway = undefined
                selectedGroup = self
                loadUserListForGroup(self)
                $('#addUserDisplayNameInput').removeAttr('disabled')
            })
            .hover(function() { $(this).toggleClass('hoverGroup ui-state-hover')})
            .prepend(groupLabel)
            .prepend(groupRights)
            .prepend(groupEditor)
            .prepend(editGroup)
            .prepend(removeGroup)
            .prepend(cancelEditGroup)
            .prepend(confirmEditGroup)
            .droppable({
                accept: ".user",
                hoverClass: 'ui-state-hover',
                drop: function( event, ui ) {
                    moveUserFromGroup1ToGroup2(selectedUser ,selectedGroup, self, function() {
                        $(event.target).effect('highlight')
                    })

                }
            });

        return group
    }

    var loadGroupList = function() {
        $.getJSON( cr + '/adminConsole/getAllGroups.action', function (data) {

            $('#groupList').empty()

            $.each(data, function () {
                $('#groupList').append(renderGroup(this));
            })
        })
    }

    loadGroupList()

	var users = []
    var selectedUser = undefined

	var loadUserListForRailway = function(railway) {
		$.getJSON(cr + '/adminConsole/getUsersByRailwayId.action', 
				{ id: railway.id },
				function (data) {
					users = data.sort(function(a,b) { return (a.displayName < b.displayName ? -1 : (a.displayName > b.displayName ? 1 : 0)) })

                    $('#userList').empty()

                    $.each(users, function() {

                        var self = this

                        var userRights = $('<div>', {
                            'html': '<span class="ui-icon ui-icon-locked"></span>',
                            'class': 'userRights',
                            'title': 'Права пользователя',
                            'click': function() {
                                openUserRightsDialog(self)
                                return false
                            }
                        })


                        var detachUser = $('<div>', {
                            'html': '<span class="ui-icon ui-icon-close"></span>',
                            'class': 'detachUser',
                            'title': 'Удалить пользователя из системы',
                            'click': function() {
                                selectedUser = self
                                pktbConfirm('Пользователь ' + self.displayName + ' будет полностью удален из системы, продолжить?', function() {
                                    $.getJSON(cr + '/adminConsole/deleteUserByCn.action', {cn: self.cn}, function() {
                                        loadUserListForRailway(selectedRailway)
                                    })
                                })
                                return false
                            }
                        })

                        var user = $('<li>', {
                            'class': 'user ui-state-default ui-corner-all',
                            'html': '<span class="userLabel">' + self.displayName +  '</span>',
                            'title': self.cn
                        })
                            .hover(function() { $(this).toggleClass('hoverUser ui-state-hover')})
                            .prepend(userRights)
                            .prepend(detachUser)
                            .draggable({
                                cursor: 'move',
                                revert: 'invalid',
                                cursorAt: { bottom: 5, right: 20 },
                                helper: function() {
                                    return $('<li>', {
                                        'class': 'user ui-state-default ui-state-active ui-corner-all',
                                        'html': '<span class="userLabel">' + self.displayName +  '</span>',
                                        'title': self.cn
                                    }).css('white-space', 'nowrap')
                                },
                                start: function() {
                                    selectedUser = self
                                    $(this).addClass('ui-state-active')
                                },
                                stop: function() { $(this).removeClass('ui-state-active') }
                            })

                        $('#userList').append(user)
                    })
				})
	}

    var loadUserListForGroup = function(group) {
        $.getJSON(cr + '/adminConsole/getUsersByGroupId.action',
            { id: group.id },
            function (data) {
                users = data.sort(function(a,b) { return (a.displayName < b.displayName ? -1 : (a.displayName > b.displayName ? 1 : 0)) })

                $('#userList').empty()

                $.each(users, function() {

                    var self = this

                    var userRights = $('<div>', {
                        'html': '<span class="ui-icon ui-icon-locked"></span>',
                        'class': 'userRights',
                        'title': 'Права пользователя',
                        'click': function() {
                            openUserRightsDialog(self)
                            return false
                        }
                    })

                    var detachUser = $('<div>', {
                        'html': '<span class="ui-icon ui-icon-close"></span>',
                        'class': 'detachUser',
                        'title': 'Удалить пользователя из группы',
                        'click': function() {
                            pktbConfirm('Удалить пользователя ' + self.displayName + ' из группы ' + selectedGroup.name + '?', function() {
                                $.getJSON(cr + '/adminConsole/detachUserByCnFromGroupById.action', {cn: self.cn, id: selectedGroup.id}, function() {
                                    loadUserListForGroup(selectedGroup)
                                })
                            })
                            return false
                        }
                    })

                    var user = $('<li>', {
                        'class': 'user ui-state-default ui-corner-all',
                        'html': '<span class="userLabel">' + this.displayName + '</span>',
                        'title': this.cn,
                        'click': function() {
                            return false
                        }
                    })
                        .hover(function() { $(this).toggleClass('hoverUser ui-state-hover')})
                        .prepend(userRights)
                        .prepend(detachUser)
                        .draggable({
                            cursor: 'move',
                            revert: 'invalid',
                            cursorAt: { bottom: 5, right: 20 },
                            helper: function() {
                                return $('<li>', {
                                    'class': 'user ui-state-default ui-state-active ui-corner-all',
                                    'html': '<span class="userLabel">' + self.displayName +  '</span>',
                                    'title': self.cn
                                }).css('white-space', 'nowrap')
                            },
                            start: function() {
                                selectedUser = self
                                $(this).addClass('ui-state-active')
                            },
                            stop: function() { $(this).removeClass('ui-state-active') }
                        })

                    $('#userList').append(user)
                })
            })
    }

    $('#addRailwayButton').click(function() {
        renderRailway({id:'',name:'',code:''}).addClass('editing').appendTo('#railwayList').find(':text:first').focus()
        var container = $('#railwayListGroupListContainer')
        container.animate({scrollTop: container[0].scrollHeight - container.height()}, 1000)
    })

    $('#addGroupButton').click(function() {
        renderGroup({id:'',name:''}).addClass('editing').appendTo('#groupList').find(':text:first').focus()
        var container = $('#railwayListGroupListContainer')
        container.animate({scrollTop: container[0].scrollHeight - container.height()}, 1000)

    })

    var loadEditRailwayForm = function(railway) {

        $('#editRailwayForm')
            .find(':input')
            .filter('[name="name"]')
            .val(railway.name)
            .end()
            .filter('[name="code"]')
            .val(railway.code)
            .end()
            .end()
            .show()
            .siblings()
            .hide()

        $('#editRailwayControls')
            .show()
            .siblings()
            .hide()
    }

    var loadEditGroupForm = function(group) {

        $('#editGroupForm input[name="name"]')
            .val(group.name)

        $('#editGroupForm')
            .show()
            .siblings()
                .hide()

        $('#editGroupControls')
            .show()
            .siblings()
                .hide()
    }

	$('#addUserDisplayNameInput')
	    .change(function () {
		    $(this).next().button('option', 'disabled', !$(this).val())
		})
		.autocomplete({
		    autoFocus:true,
			source: function (req, res) {
			    $.getJSON($('#railwayListGroupListRadioContainer :radio:eq(0)').is(':checked') ? userServiceURL : cr + '/adminConsole/usersAutocomplete.action', { q:req.term }, function() {
                    arguments[0] = arguments[0].slice(0, 5)
                    res.apply(this, arguments)
                })
			},
			select: function (event, ui) {
                addUser(ui.item)
				return false;
			},
            minLength: 2
		})
		.data('autocomplete')._renderItem = function (ul, item) {
		    return $('<li></li>')
			    .data('item.autocomplete', item)
			    .append('<a>' + item.displayName + ' [' + item.cn + ']' + (!!item.mail ? (' (' + item.mail + ')') : '') + ' </a>')
				.appendTo(ul);
		}


    var addUser = function(user) {

        var cn = user.cn,
            displayName = user.displayName

        var alreadyAttached = false

        $.each(users, function(){
            if (this.cn == cn)
                alreadyAttached = true;
        })

        if (alreadyAttached) {
            if (selectedRailway)
                pktbAlert('Пользователь ' + displayName + ' уже приписан к этой дороге.')
            else
                pktbAlert('Пользователь ' + displayName + ' уже состоит в этой группе.')

        } else {
            if (selectedRailway)
                $.getJSON(cr + '/adminConsole/attachUserByCnToRailwayById.action',
                    {
                        id: selectedRailway.id,
                        cn: cn,
                        displayName: displayName
                    },
                    function(data) {
                        if (!data.exists) {
                            loadUserListForRailway(selectedRailway)
                            $('#addUserDisplayNameInput').val('')
                        }
                        else
                            pktbConfirm('Пользователь ' + displayName + ' уже приписан к другой дороге. Изменить дорогу этого пользователя?',
                                function() {
                                    $.getJSON(cr + '/adminConsole/attachUserByCnToRailwayById.action',
                                        {
                                            id: selectedRailway.id,
                                            cn: cn,
                                            displayName: displayName,
                                            overwrite: true
                                        },
                                        function() {
                                            loadUserListForRailway(selectedRailway)
                                            $('#addUserDisplayNameInput').val('')
                                        })
                                })
                    })
            else
                $.getJSON(cr + '/adminConsole/attachUserByCnToGroupById.action',
                    {
                        id: selectedGroup.id,
                        cn: cn,
                        displayName: displayName
                    },
                    function() {
                        loadUserListForGroup(selectedGroup)
                        $('#addUserDisplayNameInput').val('')
                    })
        }
    }

    var moveUserToRailway = function(user, railway, callback) {
        $.getJSON(cr + '/adminConsole/getUsersByRailwayId.action', { id: railway.id }, function(users) {

            var alreadyAttached = false

            $.each(users, function(){
                if (this.cn == user.cn)
                    alreadyAttached = true;
            })

            if (alreadyAttached) {
                pktbAlert('Пользователь ' + user.displayName + ' уже приписан к дороге ' + railway.name + '')
            } else {
                $.getJSON(cr + '/adminConsole/attachUserByCnToRailwayById.action',
                    {
                        id: railway.id,
                        cn: user.cn,
                        displayName: user.displayName,
                        overwrite: true
                    },
                    function(data) {
                        callback()
                        loadUserListForRailway(selectedRailway)
                    })

            }
        })

    }

    var moveUserFromGroup1ToGroup2 = function(user, group1, group2, callback) {
        $.getJSON(cr + '/adminConsole/getUsersByGroupId.action', { id: group2.id }, function(users) {

            var alreadyAttached = false

            $.each(users, function(){
                if (this.cn == user.cn)
                    alreadyAttached = true;
            })

            if (alreadyAttached) {
                pktbAlert('Пользователь ' + user.displayName + ' уже приписан к группе ' + group2.name + '')
            } else {
                $.getJSON(cr + '/adminConsole/moveUserByCnFromGroup1ByIdToGroup2ById.action',
                    {
                        id1: group1.id,
                        id2: group2.id,
                        cn: user.cn,
                        displayName: user.displayName
                    },
                    function(data) {
                        callback()
                        loadUserListForGroup(selectedGroup)
                    })

            }
        })

    }

    $('#addUserForm').submit(function() { return false })




    var initUserRightsTree = function (user, group) {
        var tree = $('#userRightsTree')
        if (tree.is('.jstree'))
            tree.jstree('refresh')
        else
            tree.jstree({
                plugins:['themes', 'json_data', 'ui', 'crrm'],
                json_data:{
                    progressive_render:true,
                    ajax:{
                        url: cr + '/adminConsole/getReportsByParentReportId.action',
                        data:function (n) {
                            return {
                                id:(n.attr ? n.attr('id') : 0)
                            };
                        },
                        success:function (data) {
                            return $.map(data, function (report) {
                                var node = {};
                                node.attr = {
                                    id:report.id,
                                    'class': (report.folder ? 'folder-node' : 'report-node')
                                };
                                node.metadata = report/*{
                                    id:report.id,
                                    name:report.name,
                                    available:report.available,
                                    type:(report.folder ? 'folder' : 'report'),
                                    reportOrFolder: report
                                };*/
                                node.data = report.name;
                                report.folder && (node.state = 'closed');
                                return node;
                            })
                        }
                    }
                },
                ui:{ select_limit:1 }
            })

        tree
            .unbind('select_node.jstree deselect_node.jstree')
            .bind('select_node.jstree', function (e, data) {

                $('#giveRights')
                    .button('option', 'disabled', false)
                    .unbind('click')
                    .bind('click', function(){
                        if (user)
                            giveRights(user, $.data(data.rslt.obj[0]))
                        else
                            giveRightsToGroup(group, $.data(data.rslt.obj[0]))
                    })
            })
            .bind('deselect_node.jstree', function (e, data) {
                $('#giveRights')
                    .button('option', 'disabled', true)
                    .unbind('click')

            })
    }

    var takeAwayRights = function(user, reportOrFolder) {
        $.getJSON(cr + '/adminConsole/takeAwayRightsToReportOrFolderByIdFromUserByCn.action', {id: reportOrFolder.id, cn: user.cn }, function() {
            loadAvailible(user)
            $('#takeAwayRights').button('option', 'disabled', true)
        })
    }


    var availibleReportsOrFolder = []
    var loadAvailible = function(user) {
        $.getJSON(cr + '/adminConsole/getAvailibleForUserByCn.action', {cn: user.cn}, function(reportsOrFolders) {

            availibleReportsOrFolder = reportsOrFolders

            $('#availibleList').empty()

            $.each(reportsOrFolders, function() {

                var self = this

                var icon = $('<span>', {
                    'class': 'ui-icon ' + (self.folder ? 'folderIcon ui-icon-folder-collapsed' : 'reportIcon ui-icon-document')
                })

                var takeAwayRightsIcon = $('<div>', {
                    'html': '<span class="ui-icon ui-icon-close"></span>',
                    'class': 'takeAwayRights',
                    'title': 'Удалить права',
                    'click': function() {
                        takeAwayRights(user, self)
                        return false
                    }
                })

                var reportOrFolder = $('<li>', {
                    'class': 'reportOrFolder ui-state-default ui-corner-all',
                    'html': '<span class="reportOrFoldersLabel">' + self.name + '</span>',
                    'click': function() {
                        return false
                    }
                })
                .hover(function() { $(this).toggleClass('hoverReportOrFolder ui-state-hover')})
                .prepend(icon)
                .prepend(takeAwayRightsIcon)

                $('#availibleList').append(reportOrFolder)
            })
        })
    }

    $('#userRightsDialog')
        .dialog({
            autoOpen:false,
            modal: true,
            width:700,
            resizable: false
        })
        .css('height', 'auto')
        .parent()
        .addClass('pktb')


    var giveRights = function(user, reportOrFolder) {
        $.getJSON(cr + '/adminConsole/giveRightsToReportOrFolderByIdToUserByCn.action', {id: reportOrFolder.id, cn: user.cn }, function() {

            var alreadyAvailible = false

            $.each(availibleReportsOrFolder, function() {
                if (this.id == reportOrFolder.id)
                    alreadyAvailible = true
            })

            if (alreadyAvailible)
                if (reportOrFolder.folder)
                    pktbAlert('У пользователя ' + user.displayName + ' уже есть доступ к папке ' + reportOrFolder.name)
                else
                    pktbAlert('У пользователя ' + user.displayName + ' уже есть доступ к отчету ' + reportOrFolder.name)

            else {
                loadAvailible(user)
                $('#giveRights').add('#takeAwayRights').button('option', 'disabled', true).removeClass('ui-state-hover')
            }
        })
    }




    var openGroupRightsDialog = function(group) {
        initUserRightsTree(undefined, group)
        loadAvailibleForGroup(group)
        $('#userRightsDialog').dialog('open').dialog('option', 'title', 'Отчеты и папки доступные группе ' + group.name)
    }

    var openUserRightsDialog = function(user) {
        initUserRightsTree(user)
        loadAvailible(user)
        $('#userRightsDialog').dialog('open').dialog('option', 'title', 'Отчеты и папки доступные пользователю ' + user.displayName)
    }


    var availibleForGroupReportsOrFolder = []
    var loadAvailibleForGroup = function(group) {
        $.getJSON(cr + '/adminConsole/getAvailibleForGroupById.action', {id: group.id}, function(reportsOrFolders) {

            availibleForGroupReportsOrFolder = reportsOrFolders

            $('#availibleList').empty()

            $.each(reportsOrFolders, function() {

                var self = this

                var icon = $('<span>', {
                    'class': 'ui-icon ' + (self.folder ? 'folderIcon ui-icon-folder-collapsed' : 'reportIcon ui-icon-document')
                })

                var takeAwayRightsIcon = $('<div>', {
                    'html': '<span class="ui-icon ui-icon-close"></span>',
                    'class': 'takeAwayRights',
                    'title': 'Удалить права',
                    'click': function() {
                        takeAwayRightsFromGroup(group, self)
                        return false
                    }
                })

                var reportOrFolder = $('<li>', {
                    'class': 'reportOrFolder ui-state-default ui-corner-all',
                    'html': '<span class="reportOrFoldersLabel">' + self.name + '</span>',
                    'click': function() {
                        return false
                    }
                })
                    .hover(function() { $(this).toggleClass('hoverReportOrFolder ui-state-hover')})
                    .prepend(icon)
                    .prepend(takeAwayRightsIcon)

                $('#availibleList').append(reportOrFolder)
            })
        })
    }


    var takeAwayRightsFromGroup = function(group, reportOrFolder) {
        $.getJSON(cr + '/adminConsole/takeAwayRightsToReportOrFolderByIdFromUserGroupById.action', {rid: reportOrFolder.id, gid: group.id }, function() {
            loadAvailibleForGroup(group)
            $('#takeAwayRights').button('option', 'disabled', true)
        })
    }


    var giveRightsToGroup = function(group, reportOrFolder) {
        $.getJSON(cr + '/adminConsole/giveRightsToReportOrFolderByIdToUserGroupById.action', {rid: reportOrFolder.id, gid: group.id }, function() {

            var alreadyAvailible = false

            $.each(availibleForGroupReportsOrFolder, function() {
                if (this.id == reportOrFolder.id)
                    alreadyAvailible = true
            })

            if (alreadyAvailible)
                if (reportOrFolder.folder)
                    pktbAlert('У группы ' + group.name + ' уже есть доступ к папке ' + reportOrFolder.name)
                else
                    pktbAlert('У группы ' + group.name + ' уже есть доступ к отчету ' + reportOrFolder.name)

            else {
                loadAvailibleForGroup(group)
                $('#giveRights').add('#takeAwayRights').button('option', 'disabled', true).removeClass('ui-state-hover')
            }
        })
    }

}