

			$(document).ready(function()  {

		         //澹伴煶寮�鍏�
				$('.musicOff').bind('click', function () {
					$(this).toggleClass('musicOn');
					var krpano = document.getElementById('krpanoSWFObject');
					krpano.call("pausesoundtoggle(bgsnd)");
				})

			    //闅愯棌鏄剧ず浜у搧鍒楄〃绐楀彛
				 $(".productList").click(function () {
					$(".productPlane").slideToggle("fast",function(){
						$(".sharePlane").css({display: 'none'});
						$(".productList").toggleClass('ListActive');
						$(".share").removeClass('shareActive')
					})
				})

				//闅愯棌鏄剧ず浜у搧鍒楄〃绐楀彛
				 $(".closeList").click(function () {
					$(".productPlane").css({display: 'none'})
				 })

				//闅愯棌鏄剧ず浜岀淮鐮佺獥鍙�
				 $(".share").click(function () {
				 	$(".sharePlane").slideToggle("fast",function(){
				 		$(".productPlane").css({display: 'none'});
					 	$(".share").toggleClass('shareActive');
					 	$(".productList").removeClass('ListActive')
					})
				 })

				//闅愯棌鏄剧ず鍟嗗搧璇︽儏绐楀彛
				 $(".closeInfo").click(function () {
				 	$(".infoPlane").css({display: 'none'});
				 	$(".productPlane").css({display: 'block'})
				 });

 				 $(".list_menu img").click(function () {
					// 璇︽儏椤甸潰鍐呭閲嶇疆
					var listMenu = $(this).parent().parent();
					var productName = listMenu.find("[name='productName']").text();
					$("#productName").text(productName);
					var brandName = listMenu.find("[name='brandName']").text();
					$("#brandName").text(brandName);
					var totalPrice = listMenu.find("[name='totalPrice']").text();
					$("#totalPrice").text(totalPrice);
					var count = listMenu.find("[name='count']").text();
					$("#count").text(count);
					var productModelNumber = listMenu.find("[name='productModelNumber']").val();
					$("#productModelNumber").text(productModelNumber);
					var productSpec = listMenu.find("[name='productSpec']").val();
					$("#productSpec").text(productSpec);
					var productDesc = listMenu.find("[name='productDesc']").val();
					$("#productDesc").text(productDesc);
					var productOriginalPicPath = listMenu.find("[name='productOriginalPicPath']").val();
					$("#productOriginalPicPath").attr("src",productOriginalPicPath);

					productOriginalPicPath
				 	$(".infoPlane").css({display: 'block'});
				 	$(".productPlane").css({display: 'none'});
				 })

				//鍏抽棴搴曢儴鏍囨敞鎻愮ず

				$(".close_bz_Info").click(function () {
				 	$(".bz_info").css({display: 'none'})
				});
			})

				
