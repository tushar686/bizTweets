var page = {mappings: [{}]}

function loadMappings() {
	setHighlitedMenu(3);
	generateDraggableDroppable();
}

function generateDraggableDroppable() {
	$("#content").html(generateDroppableDIVs() + generateDraggableDIVsForAllEntities());
	
	setAllEntitiesDraggable();	
	setDroppableBehaviour();
}

function generateDraggableDIVsForAllEntities() {
	var div_entities = ""; 
	$.each(global.entities, function (index, schema) {
		jsonSchema = $.parseJSON(schema);		
		div_entities = div_entities + "<div id='draggable" + index + "' class='ui-widget-content' lang='"+ jsonSchema.entity +"'>" +  jsonSchema.entity.replace(/\,/g,' | ') + "</div>"; 
	});
	
	return div_entities;
}

function generateDroppableDIVs() {
	var droppable = "<div id='droppable' class='ui-widget-header container'>"
	  + "<p>Drop Entities here to map them under this name: <input type='text' /></p>"
	  + "</div></br>";
	
	return droppable;
}

function setAllEntitiesDraggable() {
	$.each(global.entities, function (index, schema) {
		$('#draggable' + index).draggable();
	});
}

function setDroppableBehaviour() {
	$('#droppable').droppable({
		drop: function(event, ui) {
			page.mappings.push({entity: ui.draggable.attr("lang")});			
			console.log(page.mappings[1].entity);
		}
	});
}
