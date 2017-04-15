var main = function(){
	var image1 = document.getElementById('1_image');
	var image2 = document.getElementById('2_image');
	var image3 = document.getElementById('3_image');
	var image4 = document.getElementById('4_image');
	var cpu = [12, 9, 7, 8, 5];
	var mrmory =[2, 1, 3.5, 7, 3];
	var diskspace=[1, 3, 4, 5, 6]
	var ram =[3, 4, 5, 6, 7];
	if(image1 !=""){
		click(cpu);
	}
	if(image2 !=""){
		click(mrmory);
	}
	if(image1 !=""){
		click(diskspace);
	}
	if(image1 !=""){
		click(ram);
	}
	var click = function(e){
		new Chartist.Line('.ct-chart', {
			labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
			series: [e]
		}, 
		{
		fullWidth: true,
		chartPadding: {
			right: 40
			}
		});
	};
};	

$(document).ready(main);