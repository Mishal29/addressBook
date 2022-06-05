$(function(){
	$('#button').bind("click",function(){
		var re = new RegExp($('#search').val());
		var column = $('[name="column"] option:selected').val();
		$('#target tbody tr').each(function(){
			var txt = $(this).find("td:eq(" + column + ")").html();
			if(txt.match(re) != null){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	});

	$('#reset').bind("click",function(){
		$('#search').val('');
		$('#target tr').show();
	});
});