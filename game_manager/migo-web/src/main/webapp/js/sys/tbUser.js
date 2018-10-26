$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/tbUser/list',
        datatype: "json",
        colModel: [
			{ label: '用户ID', name: 'userId', hidden:true,index: "user_id", width: 45, key: true },
			{ label: '用户名', name: 'username', width: 75 },
			{ label: '手机号', name: 'mobile', width: 100 },
            { label: '邮箱', name: 'email', width: 100 },
			{ label: '状态', name: 'status', align:'center', width: 40, formatter: function(value, options, row){
				var r = value === 0 ?
                    '<span class="label label-danger">禁用</span> ' :
                    '<span class="label label-success">正常</span>' ;
				return r;
			}},
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 80},
            {
                label: '操作', name: 'status', width: 40, formatter: function (value,options,row) {
                    var r = value === 0 ?
                        '<span class="btn-sm btn-primary" onclick="changeStatus(1,'+row.userId+')">恢复用户</span> ' :
                        '<span class="btn-sm btn-primary" onclick="changeStatus(0,'+row.userId+')">禁用用户</span>' ;
                    return r;
                }
            }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "data",
            page: "currPage",
            total: "totalPage",
            records: "totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			search: null
		},
		showList: true,
		title:null
	},
	methods: {
		query: function () {
			vm.reload();
		},

		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'search': vm.q.search},
                page:page
            }).trigger("reloadGrid");
		}
	}
});



function changeStatus(s,userId) {
    confirm("确定要更改用户状态吗",function () {
        $.ajax({
            type: "POST",
            url: "../sys/tbUser/changeStatus",
            dataType:"json",
            contentType:"application/x-www-form-urlencoded",
            data: {status:s,userId:userId},
            success: function (r) {
                if (r.code === 0) {
                    alert('操作成功', function () {
                        vm.reload();
                    });
                } else {
                    alert(r.message);
                }
            }
        });
    })
}