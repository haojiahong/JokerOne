jQuery.fn.rotate = function(angle,whence) {
	var p = this.get(0);

	// we store the angle inside the image tag for persistence
	if (!whence) {
		p.angle = ((p.angle==undefined?0:p.angle) + angle) % 360;
	} else {
		p.angle = angle;
	}

	if (p.angle >= 0) {
		var rotation = Math.PI * p.angle / 180;
	} else {
		var rotation = Math.PI * (360+p.angle) / 180;
	}
	var costheta = Math.round(Math.cos(rotation) * 1000) / 1000;
	var sintheta = Math.round(Math.sin(rotation) * 1000) / 1000;
	//alert(costheta+","+sintheta);
 
	if (document.all && !window.opera) {
		var canvas = document.createElement('img');

		canvas.src = p.src;
		canvas.height = p.height;
		canvas.width = p.width;

		canvas.style.filter = "progid:DXImageTransform.Microsoft.Matrix(M11="+costheta+",M12="+(-sintheta)+",M21="+sintheta+",M22="+costheta+",SizingMethod='auto expand')";
	} else {
		var canvas = document.createElement('canvas');
		if (!p.oImage) {
			canvas.oImage = new Image();
			canvas.oImage.src = p.src;
		} else {
			canvas.oImage = p.oImage;
		}

		canvas.style.width = canvas.width = Math.abs(costheta*canvas.oImage.width) + Math.abs(sintheta*canvas.oImage.height);
		canvas.style.height = canvas.height = Math.abs(costheta*canvas.oImage.height) + Math.abs(sintheta*canvas.oImage.width);

		var context = canvas.getContext('2d');
		context.save();
		if (rotation <= Math.PI/2) {
			context.translate(sintheta*canvas.oImage.height,0);
		} else if (rotation <= Math.PI) {
			context.translate(canvas.width,-costheta*canvas.oImage.height);
		} else if (rotation <= 1.5*Math.PI) {
			context.translate(-costheta*canvas.oImage.width,canvas.height);
		} else {
			context.translate(0,-sintheta*canvas.oImage.width);
		}
		context.rotate(rotation);
		context.drawImage(canvas.oImage, 0, 0, canvas.oImage.width, canvas.oImage.height);
		context.restore();
	}
	canvas.id = p.id;
	canvas.angle = p.angle;
	p.parentNode.replaceChild(canvas, p);
}

jQuery.fn.rotateRight = function(angle) {
	this.rotate(angle==undefined?90:angle);
}

jQuery.fn.rotateLeft = function(angle) {
	this.rotate(angle==undefined?-90:-angle);
}

if (window.addEventListener) 
{ 
   window.addEventListener('DOMMouseScroll', wheel, false);//给firefox添加鼠标滚动事件 
} 
 
function wheel(event) 
{ 
   return wheelimg(event); 
} 
function bbimg(obj) 
{ 	
} 
   
 function wheelimg(event) 
 { 
  var delta = 0; 
  var div = document.getElementById("img_div_wrap"); 
  if (event.detail) 
  {  
      //如果是firefox 
      var allImg = div.getElementsByTagName("img"); 
      var isThis=false;//现判断鼠标中仑的元素是不是包含在那个div里面 
      for(i=0;i<allImg.length;i++) 
       { 
             isThis=true; 
             allImg[i].width=allImg[i].width+event.detail*12; 
          event.returnValue = false; 
       } 
        
   } 
    return true; 
  } 

var view_no=0;
function pwiw_init(path,thums,index,maxW,maxH) {
	if(view_no!=0) return;
	view_no+=1;
	var divheight = $(window.top.document).height();
	var divwidth = $(window.top.document).width();
	var ni = new Image();
	ni.src = path+"&d="+new Date().getTime();// "/ytszgl/szglImages/064a2a2e1d6e4802b2cfba020373521b.jpg";//
	ni.onload = function(){
		var imgW,imgH,maxW=900,maxH=600;
		maxW = typeof(maxW) == 'undefined'?900:maxW;
		maxH = typeof(maxH) == 'undefined'?900:maxH;
		var hRatio;
		var wRatio;
		var Ratio = 1;
		var w = ni.width;
		var h = ni.height;
		wRatio = maxW / w;
		hRatio = maxH / h;
		if (maxW == 0 && maxH == 0) {
			Ratio = 1;
		} else if (maxW == 0) {//
			if (hRatio < 1)
				Ratio = hRatio;
		} else if (maxH == 0) {
			if (wRatio < 1)
				Ratio = wRatio;
		} else if (wRatio < 1 || hRatio < 1) {
			Ratio = (wRatio <= hRatio ? wRatio : hRatio);
		}
		if (Ratio < 1) {
			w = w * Ratio;
			h = h * Ratio;
		}
		
		var divheight = $(window.top.document).height();
		var divwidth = $(window.top.document).width()*7/10;
		if(typeof(thums)=='undefined'||thums.length<=0)
			divwidth = $(window.top.document).width();
		
		var $img = $('<div id="img_div_wrap" onmousewheel="bbimg(this)" style="position:absolute;top:50%;left:50%;" ></div>');
		var $imgContent = $('<img id="piew_img" />');
		$img.append($imgContent.css({
			'padding':'4px',
			'width':'100%',
			'height':'100%',
			'background-color':'white'
		}));
		
		$img.css({'margin-top':(-h/2)+'px','margin-left':(-w/2)+'px'});
	
		var $mid = $('<div id="" class="boxgallery"/>').css({
			"position" : "absolute",
			"top" : '0px',
			"left" : "0px",
			"width" : '100%',
			"height" : '100%'
		});
		
		var $box = $('<div id="" class="picturesbox" />');
		
		var $s = $('<div />').css({
			"position" : "absolute",
			"background-color" : "rgba(95, 95, 95, 0.97)",
			"height" : divheight,
			"overflow" : "hidden",
			"width" : '100%',
			"z-index" : "3000",
			"top" : '0px',
			"left" : "0px"
		})
		
		var $closeBtn = $('<div class="closeBtn"></div>').click(function(){
			$box.remove();
			$s.remove();			view_no-=1;
		});
		
		var $prevOneBtn = $('<span class="prev"><i></i></span>');
		var $nextOneBtn = $('<span class="next"><i></i></span>');
		
		var $roteRight = $('<span class="roteRight"><i></i></span>');
		$mid.append($('<div class="rote">').append($roteRight).append($prevOneBtn).append($nextOneBtn));
		$mid.append($img);
		
		$box.append($mid);
		$box.append($closeBtn);
		
		$(window.top.document.body).append($s);
		$(window.top.document.body).append($box);
		var screenCenter = webPage.center({left:0,top:0})
		$img.animate({width:w,height:h});
		$img.find('#piew_img').attr('src', path);
		
		
		if(typeof(thums)=='undefined'||thums.length<=0) return;
		
		var neiRizePic = function(src){
			resizePic(src,function(wx,hx){
				$imgContent.animate({width:wx,height:hx});
				$img.css({width:wx,height:hx,'margin-top':(-hx/2)+'px','margin-left':(-wx/2)+'px'});
				$img.find('#piew_img').attr('src', src);
			})
		}
		
		var $thumbBox = $('<div class="thumbBox">');
		var $thumbList = $('<div class="thumbList">');
		var $topBtn = $('<div class="prev-pic">');
		var $topBtnWrap = $('<div class="btn-wrap top">').append($topBtn);
		var $bottomBtn = $('<div class="next-pic">');
		var $bottonBtnWrap = $('<div class="btn-wrap bottom">').append($bottomBtn);
		
		var $thumbContent = $('<div class="thumbContent">');
		
		$thumbList.append($thumbContent);
		$thumbList.append($topBtnWrap);
		$thumbList.append($bottonBtnWrap);
		
		$thumbBox.append($thumbList);
		$box.append($thumbBox);
		$nextOneBtn.click(function(){
			var $curSelected = $thumbContent.find('.item.selected');
			var $next = $curSelected.parent().next().find('.item');;
			if(!$next[0]) return;
			$thumbContent.find('.item').removeClass('selected');
			$next.addClass('selected');
			neiRizePic($next.attr('picsrc'));
		})
		
		$prevOneBtn.click(function(){
			var $curSelected = $thumbContent.find('.item.selected');
			var $prev = $curSelected.parent().prev().find('.item');
			if(!$prev[0]) return;
			$thumbContent.find('.item').removeClass('selected');
			$prev.addClass('selected');
			neiRizePic($prev.attr('picsrc'));
		})
		var ror = 0;
		$roteRight.click(function(){
			ror += 90;
			$img.css({'-moz-transform':'rotate('+ror+'deg)', '-webkit-transform':'rotate('+ror+'deg)'});
		});
		for(var i=0;i<thums.length;i++){
			var picObj = thums[i];
			if(!index) index = 0;
			if(i==index){
				$thumbContent.append('<div class="item-wrap"><div class="item selected" picsrc="'+picObj.pic+'"><img src="'+picObj.thumb+'" /></div></div>');
				neiRizePic(picObj.pic);
			}else
				$thumbContent.append('<div class="item-wrap"><div class="item" picsrc="'+picObj.pic+'"><img src="'+picObj.thumb+'" /></div></div>');
		}
		
		var slip=10;//缩略图中间缝隙
		var moveCurTime = 0;
		var moveHis = []; //运动历史记录
		$topBtn.click(function(){
			var contentHeight = $thumbList.height()-$topBtnWrap.height()*2;
			var moveAllTimes = ((80+slip)*10)%contentHeight>0?parseInt(((80+slip)*10)/contentHeight+1):parseInt(((80+slip)*10)/contentHeight);
			
			if(moveCurTime>=moveAllTimes-1) return ;
			moveCurTime++;	
			if(contentHeight%(80+slip)>0){
				var viewLast = parseInt(contentHeight/(80+slip));
			}
			if(typeof(moveHis)=='undefined'||moveHis.length==0) {
				moveHis = [];
				return;
			}
			var lastMove = ((viewLast-1)*(80+slip));
			moveHis.push(lastMove);
			$thumbContent.animate({top:'-='+lastMove});
		})
		$bottomBtn.click(function(){
			if(typeof(moveHis)=='undefined'||moveHis.length==0) return;
			moveCurTime--;
			var lastMove = moveHis[moveHis.length-1];
			moveHis = moveHis.slice(0,moveHis.length-1);
			$thumbContent.animate({top:'+='+lastMove});
		})
		$thumbContent.find('.item').click(function(){
			neiRizePic($(this).attr('picsrc'));
			$thumbContent.find('.item').removeClass('selected');
			$(this).addClass('selected');
		});
		
	}
}

function resizePic(path,callback){
	var divheight = $(window.top.document).height();
	var divwidth = $(window.top.document).width();
	
	var ni = new Image();
	ni.src = path+"&d="+new Date().getTime();// "/ytszgl/szglImages/064a2a2e1d6e4802b2cfba020373521b.jpg";//
	ni.onload = function(){
		var imgW,imgH,maxW=900,maxH=600;
		maxW = typeof(maxW) == 'undefined'?900:maxW;
		maxH = typeof(maxH) == 'undefined'?900:maxH;
		var hRatio;
		var wRatio;
		var Ratio = 1;
		var w = ni.width;
		var h = ni.height;
		wRatio = maxW / w;
		hRatio = maxH / h;
		if (maxW == 0 && maxH == 0) {
			Ratio = 1;
		} else if (maxW == 0) {//
			if (hRatio < 1)
				Ratio = hRatio;
		} else if (maxH == 0) {
			if (wRatio < 1)
				Ratio = wRatio;
		} else if (wRatio < 1 || hRatio < 1) {
			Ratio = (wRatio <= hRatio ? wRatio : hRatio);
		}
		if (Ratio < 1) {
			w = w * Ratio;
			h = h * Ratio;
		}
		callback(w,h);
	}
}

var webPage = (function () {
      var b = window.top.document;
      var e = window.top.document.documentElement;
      return {
          // 当前网页的总大小
          size: function () {
              // 结合上一篇博文中scrollXxx等几种属性的描述
              var w = Math.max(b.scrollWidth, b.offsetWidth, e.scrollWidth);
              var h = Math.max(b.scrollHeight, b.offsetHeight, e.scrollHeight);
              return { "width": w * 1, "height": h * 1 };
          },
          // 当前视窗的屏幕中心位置
          center: function (pos) {// pos偏移{left:value,top:value}
              // 当前视窗中心点位置相对网页左上角的像素位置＝当前视窗大小/2 + 滚动条偏移值
              var lt = this.leftTop();
              var x = e.clientWidth / 2 + lt.x;
              var y = e.clientHeight / 2 + lt.y;
              if (pos) {
                  if (pos.left * 1) {
                      x = x + pos.left * 1;
                  }
                  if (pos.top * 1) {
                      y = y + pos.top * 1;
                  }
              }
              return { "x": x, "y": y };
          },
          // 当前视窗左上角相对当前网页左上角的像素位置＝＝滚动条偏移量
          leftTop: function () {
              // e.scrollLeft：IE下为偏移值，其它为0 ，b.scrollLeft:IE为0,Chrome等为偏移值
              return { "x": (b.body.scrollLeft + e.scrollLeft) * 1, "y": (b.body.scrollTop + e.scrollTop) * 1 };
          },
          // 当前视窗右下角相对当前网页左上角的像素位置＝＝当前视窗大小+滚动条偏移量
          rightBottom: function () {
              var lt = this.leftTop();
              return { "x": e.clientWidth + lt.x, "y": e.clientHeight + lt.y };
          }
      }
} ());