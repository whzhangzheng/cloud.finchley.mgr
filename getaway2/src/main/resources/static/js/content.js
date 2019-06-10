var $parentNode = window.parent.document;

function $childNode(name) {
    return window.frames[name]
}

// tooltips
$('.tooltip-demo').tooltip({
    selector: "[data-toggle=tooltip]",
    container: "body"
});

// 使用animation.css修改Bootstrap Modal
$('.modal').appendTo("body");

$("[data-toggle=popover]").popover();

//折叠ibox
$('.collapse-link').click(function () {
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.find('div.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

//关闭ibox
$('.close-link').click(function () {
    var content = $(this).closest('div.ibox');
    content.remove();
});

//判断当前页面是否在iframe中
//if (top == this) {
//    var gohome = '<div class="gohome"><a class="animated bounceInUp" href="index.html?v=4.0" title="返回首页"><i class="fa fa-home"></i></a></div>';
//    $('body').append(gohome);
//}

//animation.css
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function () {
            element.addClass('animated ' + animation);
        },
        function () {
            //动画完成之前移除class
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}

//拖动面板
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable({
            handle: handle,
            connectWith: connect,
            tolerance: 'pointer',
            forcePlaceholderSize: true,
            opacity: 0.8,
        })
        .disableSelection();
};


//编辑器新增的ajax上传图片函数
function sendFile(files, editor, $editable) {
    var size = files[0].size;
    if((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    console.log("size="+size);
    var formData = new FormData();
    formData.append("file", files[0]);
    $.ajax({
        data : formData,
        type : "POST",
        url : appName+"/upload/batch/upload",    // 图片上传出来的url，返回的是图片上传后的路径，http格式
        cache : false,
        contentType : false,
        processData : false,
        dataType : "json",
        success: function(data) {//data是返回的hash,key之类的值，key是定义的文件名
            $('.summernote').summernote('insertImage',appName+'/upload/readImg?attachName='+data.filename);
        },
        error:function(){
            alert("上传失败");
        }
    });
}

var readCountButton = function (context) {
	 var ui = $.summernote.ui;
	 var button = ui.button({
	    contents: '<i class="fa fa-child"/> 阅读量',
	    tooltip: '阅读量代码块',
	    click: function () {

	      context.invoke('editor.pasteHTML', '<ul class="like_share_block"><li>阅读量:<span th:text="${vo.readCount}">0</span></li><li style="text-align: right"><a id="evenLike" onclick="evenLike(this)"><i class="iconfont share_ico_pyq" th:style="${vo.like}?\'color:red;padding-right: 5px;\':\'padding-right: 5px;\'">&#xe688;</i></a><span th:text="${vo.likeCount}" id="likeCount">0</span></li></ul><br/>');
	    }
	 });
	 return button.render();
}


var liuyanButton = function (context) {
	 var ui = $.summernote.ui;
	 var button = ui.button({
	    contents: '<i class="fa fa-child"/> 留言',
	    tooltip: '留言代码块',
	    click: function () {
	      context.invoke('editor.pasteHTML', '<div class="pet_comment_list" style="padding-top: 0px"><div class="pet_comment_list_wap" style="background: #f2f2f2;"><div class="pet_comment_list_title" ><span id="leaveMsgTitle">精彩评论</span><span class="writememo" id="leaveMsgSubmit">评论</span></div>    <div data-am-widget="tabs" class="am-tabs am-tabs-default pet_comment_list_tab" >        <div class="am-tabs-bd pet_pl_list content" id="content_leaveMsg" style="background-color: #f2f2f2;">        <ul data-tab-panel-0 class="am-tab-panel am-active " id="leaveMsgList">        </ul> </div> </div> </div> </div>');
	    }
	 });
	 return button.render();


}
