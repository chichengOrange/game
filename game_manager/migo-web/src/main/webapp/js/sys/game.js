$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/game/list',
        datatype: "json",
        colModel: [
            {label: '课件ID', name: 'id', /*hidden: true,*/ index: 'id', width: 60, key: true},
            {label: '课件名称', name: 'name', width: 75},
            {label: '课件标题', name: 'title', width: 90},
            {label: '课件描述', name: 'description', width: 100},
            {
                label: '图片', name: 'picture', width: 30, formatter: function (value, options, row) {
                    return value != null ?
                        '<a data-toggle="modal" data-target="#layer" onclick="showPic(\'' + value + '\')">查看图片</a>' :
                        '<span class="label label-danger">暂无图片</span>';
                }
            },
            {
                label: '视频', name: 'video', width: 30, formatter: function (value) {
                    return value != null ?
                        '<a href="../download?path=' + value + '"  download="" ">下载视频</a>' :
                        '<span class="label label-danger">暂无视频</span>';
                }
            },
            {
                label: '课件', name: 'appPackage', width: 40, formatter: function (value) {
                    return value != null ?
                        '<a href="../download?path=' + value + '" download="" ">下载程序包</a>' :
                        '<span class="label label-danger">暂无程序包</span>';
                }
            },
            {label: '版本', name: 'version', width: 30},
            {label: '下载量', name: 'downloadCount', width: 40},
            {label: '创建时间', name: 'createTime', index: "create_time", width: 60},
            {
                label: '操作', name: 'id', width: 30, formatter: function (value) {
                    return '<a class="btn-sm btn-primary" onclick="update(\'' + value + '\')" ><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>'
                }
            }

        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
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
        add: function () {
            $('#form').data('bootstrapValidator', null);
            formValidator();
            vm.showList = false;
            vm.title = "新增";
            vm.game = {};
            var prevDiv = document.getElementById('preview');
            prevDiv.style.display = "none";

            $(".myAtt").val("");
        },
        /*update: function () {
            $('#form').data('bootstrapValidator', null);
            formValidator();
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.getGame(id);
            previewPic();
        },*/


        del: function () {
            var gameIds = getSelectedRows();
            if (gameIds == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../sys/game/delete",
                    data: JSON.stringify(gameIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var bootstrapValidator = $("#form").data('bootstrapValidator');
            bootstrapValidator.validate();
            if (!bootstrapValidator.isValid()) {
                return;
            }
            var formData = new FormData();

            var pictureAtt = $('#pictureAtt')[0];

            if (vm.game.id == null && pictureAtt.files.length == 0) {
                alert("图片不能为空");
                return;
            }
            if (pictureAtt.files.length > 0) {
                formData.append("pictureAtt", pictureAtt.files[0]);
            }

            var videoAtt = $('#videoAtt')[0];
            if (videoAtt.files.length > 0) {
                formData.append("videoAtt", videoAtt.files[0]);
            }

            var packageAtt = $('#packageAtt')[0];
            if (packageAtt.files.length > 0) {
                formData.append("packageAtt", packageAtt.files[0]);
            }

            for (var key in vm.game) {
                var val = vm.game[key];
                if (val != null)
                    formData.append(key, val);
            }

            var url = vm.game.id == null ? "../sys/game/save" : "../sys/game/update";

            $("body").mLoading("show");

            var oReq = new XMLHttpRequest();
            oReq.onreadystatechange = function (ev) {
                if (oReq.readyState == 4) {
                    if (oReq.status == 200) {
                        var json = JSON.parse(oReq.responseText);
                        if (json.code === 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(json.message);
                        }
                        $("body").mLoading("hide");
                    } else {
                        $("body").mLoading("hide");
                        alert("网络故障")
                    }
                }
            };
            oReq.open("POST", url);
            oReq.send(formData);

            /* $.ajax({
                 type: "POST",
                 url: url,
                 contentType:"multipart/form-data",
                 data: formData,
                 success: function (r) {
                     if (r.code === 0) {
                         alert('操作成功', function (index) {
                             vm.reload();
                         });
                     } else {
                         alert(r.message);
                     }
                 },
                 error:function(){
                     alert("网络故障");
                 },
                 complete:function () {
                     $("body").mLoading("hide");
                 }
             });*/
        },
        getGame: function (id) {
            $.get("../sys/game/info/" + id, function (r) {
                vm.game = r.data;
                //展示图片
                if (r.data.picture != null) {
                    previewPic();
                }
            });
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


function showPic(url) {
    $("#pic").attr("src", url);
}


function previewPic() {
    var prevDiv = document.getElementById('preview');
    prevDiv.style.display = "block";
    prevDiv.innerHTML = '<img style="max-width: 180px" src="' + vm.game.picture + '" />';
}


function update(id) {
    $('#form').data('bootstrapValidator', null);
    formValidator();
    vm.showList = false;
    vm.title = "修改";
    vm.getGame(id)
    $(".myAtt").val("");

}




