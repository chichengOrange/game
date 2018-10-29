$(function () {
    $("#jqGrid").jqGrid({
        url: 'http://47.99.61.151:9002/app/gameReplay/list',
        datatype: "json",
        colModel: [

            {label: '课件名称', name: 'gameName', width: 75},
            {label: '考试人', name: 'userName', width: 60},
            {
                label: '记录文件',
                name: 'replayFile',
                width: 30,
                align: 'center',
                sortable: false,
                formatter: function (value, options, data) {
                    return value != null && value != "" ?
                        '<a style="color:#337ab7;text-decoration: underline darkcyan"  href="http://47.99.61.151:9002/app/download?path=' + value + '" download="">下载记录</a>' :
                        '<span style="color: red">暂无记录</span>';
                }
            },
            {
                label: 'visaFile',
                name: 'visaFile',
                width: 30,
                align: 'center',
                sortable: false,
                formatter: function (value, options, data) {
                    return value != null && value != "" ?
                        '<a style="color:#337ab7;text-decoration: underline darkcyan"  href="http://47.99.61.151:9002/app/gameReplay/download?replayId=' + data.id + '" download="">下载</a>' :
                        '<span style="color: red">暂无visa</span>';
                }
            },
            {label: '记录内容', name: 'replayContent', align: 'center', width: 130, sortable: false},
            {label: 'createTime', name: 'createTime', index: 'create_time', align: 'center', width: 100}

        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data",
            page: "currPage",
            total: "totalPage",
            records: "totalCount"
        },
        prmNames: {
            page: "page",
            rows: "pageSize",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            search: null
        },
        showList: true,
        title: null,
        gameList: [],
        user: {
            status: 1,
            roleIdList: []
        },
        game: {
            id: null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'search': vm.q.search},
                page: page
            }).trigger("reloadGrid");
        }
    }
});


