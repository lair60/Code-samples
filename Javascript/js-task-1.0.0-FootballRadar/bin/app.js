		var websocket
		var CHAMPIONS_LEAGUE = "rgb(0, 158, 255)";
		var EUROPE_LEAGUE = "darkseagreen";
		var RELEGATION = "indianred";
		var OTHER = "white";
		var ASC_ORDER = 0;
		var DESC_ORDER = 1;
		var NAME = 2;
		var PLAYED = 3;
		var WON = 4;
		var DRAWN = 5;
		var LOST = 6;
		var GOALS_FOR = 7;
		var GOALS_AGAINST = 8;
		var GOAL_DIFFERENCE = 9;
		var POINTS = 10;
		var URL_GAMES = "ws://localhost:8080/games"
		var URL_TEAMS = "http://localhost:8080/teams"
		var NUMBER_PACKAGES = 0
		//Start connection with server(using WebSocket)
		function startConnection(){
			showEmptyTable()
			$("#stop").show()
			$("#start").hide()
			$("#selectpicker").prop('disabled', true);
			$("#clear").prop('disabled', true);
			websocket = new WebSocket(URL_GAMES); //we create the socket
			websocket.onopen = function(evt) { 
				$("#errorMessage2").hide()
			};
			NUMBER_PACKAGES = 0
			websocket.onmessage = function(evt) { // When we receive some msg
				var jsonData = JSON.parse(evt.data);
				setMatchOnTable(jsonData.homeTeamId,jsonData.awayTeamId,jsonData.homeGoals,jsonData.awayGoals)
				NUMBER_PACKAGES++;
				if (NUMBER_PACKAGES==20*19){//All packages were sent.
					stopConnection()
				}
			};
			websocket.onerror = function(evt) {
				$("#errorMessage2").show()
			};
			websocket.onclose = function(evt) { 
				$("#stop").hide()
				$("#start").show()
				$("#selectpicker").prop('disabled', false);
				$("#clear").prop('disabled', false);
			};
		}
		//Stops connection with websocket
		function stopConnection(){
			websocket.close()
			$("#stop").hide()
			$("#start").show()
			$("#selectpicker").prop('disabled', false);
			$("#clear").prop('disabled', false);
		}
		//Process the data and update the table	
		function setMatchOnTable(homeTeamId,awayTeamId,homeGoals,awayGoals){
			if (homeGoals>awayGoals){
				var row = $("#tableSeason").find("#"+homeTeamId)[0]
				row.cells[PLAYED].innerHTML = parseInt(row.cells[PLAYED].innerHTML) + 1;//Played
				row.cells[WON].innerHTML = parseInt(row.cells[WON].innerHTML) + 1;//Won
				row.cells[GOALS_FOR].innerHTML = parseInt(row.cells[GOALS_FOR].innerHTML) + parseInt(homeGoals);//Goals for
				row.cells[GOALS_AGAINST].innerHTML = parseInt(row.cells[GOALS_AGAINST].innerHTML) + parseInt(awayGoals);//Goals against
				row.cells[GOAL_DIFFERENCE].innerHTML = parseInt(row.cells[GOALS_FOR].innerHTML) - parseInt(row.cells[GOALS_AGAINST].innerHTML);//Goal difference
				row.cells[POINTS].innerHTML = parseInt(row.cells[POINTS].innerHTML) + 3;//Points
				
				row = $("#tableSeason").find("#"+awayTeamId)[0]
				row.cells[PLAYED].innerHTML = parseInt(row.cells[PLAYED].innerHTML) + 1;//Played
				row.cells[LOST].innerHTML = parseInt(row.cells[LOST].innerHTML) + 1;//Lost
				row.cells[GOALS_FOR].innerHTML = parseInt(row.cells[GOALS_FOR].innerHTML) + parseInt(awayGoals);//Goals for
				row.cells[GOALS_AGAINST].innerHTML = parseInt(row.cells[GOALS_AGAINST].innerHTML) + parseInt(homeGoals);//Goals against
				row.cells[GOAL_DIFFERENCE].innerHTML = parseInt(row.cells[GOALS_FOR].innerHTML) - parseInt(row.cells[GOALS_AGAINST].innerHTML);//Goal difference
			}
			else if(homeGoals==awayGoals){
					var row = $("#tableSeason").find("#"+homeTeamId)[0]
					row.cells[PLAYED].innerHTML = parseInt(row.cells[PLAYED].innerHTML) + 1;//Played
					row.cells[DRAWN].innerHTML = parseInt(row.cells[DRAWN].innerHTML) + 1;//Drawn
					row.cells[GOALS_FOR].innerHTML = parseInt(row.cells[GOALS_FOR].innerHTML) + parseInt(homeGoals);//Goals for
					row.cells[GOALS_AGAINST].innerHTML = parseInt(row.cells[GOALS_AGAINST].innerHTML) + parseInt(awayGoals);//Goals against
					row.cells[POINTS].innerHTML = parseInt(row.cells[POINTS].innerHTML) + 1;//Points
					row = $("#tableSeason").find("#"+awayTeamId)[0]
					row.cells[PLAYED].innerHTML = parseInt(row.cells[PLAYED].innerHTML) + 1;//Played
					row.cells[DRAWN].innerHTML = parseInt(row.cells[DRAWN].innerHTML) + 1;//Drawn
					row.cells[GOALS_FOR].innerHTML = parseInt(row.cells[GOALS_FOR].innerHTML) + parseInt(homeGoals);//Goals for
					row.cells[GOALS_AGAINST].innerHTML = parseInt(row.cells[GOALS_AGAINST].innerHTML) + parseInt(awayGoals);//Goals against
					row.cells[POINTS].innerHTML = parseInt(row.cells[POINTS].innerHTML) + 1;//Points
				}
				else{
					var row = $("#tableSeason").find("#"+awayTeamId)[0]
					row.cells[PLAYED].innerHTML = parseInt(row.cells[PLAYED].innerHTML) + 1;//Played
					row.cells[WON].innerHTML = parseInt(row.cells[WON].innerHTML) + 1;//Won
					row.cells[GOALS_FOR].innerHTML = parseInt(row.cells[GOALS_FOR].innerHTML) + parseInt(awayGoals);//Goals for
					row.cells[GOALS_AGAINST].innerHTML = parseInt(row.cells[GOALS_AGAINST].innerHTML) + parseInt(homeGoals);//Goals against
					row.cells[GOAL_DIFFERENCE].innerHTML = parseInt(row.cells[GOALS_FOR].innerHTML) - parseInt(row.cells[GOALS_AGAINST].innerHTML);//Goal difference
					row.cells[POINTS].innerHTML = parseInt(row.cells[POINTS].innerHTML) + 3;//Points
					
					row = $("#tableSeason").find("#"+homeTeamId)[0]
					row.cells[PLAYED].innerHTML = parseInt(row.cells[PLAYED].innerHTML) + 1;//Played
					row.cells[LOST].innerHTML = parseInt(row.cells[LOST].innerHTML) + 1;//Lost
					row.cells[GOALS_FOR].innerHTML = parseInt(row.cells[GOALS_FOR].innerHTML) + parseInt(homeGoals);//Goals for
					row.cells[GOALS_AGAINST].innerHTML = parseInt(row.cells[GOALS_AGAINST].innerHTML) + parseInt(awayGoals);//Goals against
					row.cells[GOAL_DIFFERENCE].innerHTML = parseInt(row.cells[GOALS_FOR].innerHTML) - parseInt(row.cells[GOALS_AGAINST].innerHTML);//Goal difference
				}
			orderTable();
			$("#tableSeason").find("#"+homeTeamId).fadeOut();
			$("#tableSeason").find("#"+awayTeamId).fadeOut();
			$("#tableSeason").find("#"+homeTeamId).fadeIn(500);
			$("#tableSeason").find("#"+awayTeamId).fadeIn(500);
		}
		//Show the table with all teams from the beginning
		function showEmptyTable(){
			$("#start").show()
			$("#clear").show()
			$("#tableSeason").remove()
			$.get(URL_TEAMS,function(data){
					cad = ''
					for(i=0;i<data.length;i++){							
						cad = cad + '<tr id='+data[i].id+'><td width="8%">0</td><td width="8%">'+data[i].id+'</td>'+'<td width="28%">'+data[i].name+'</td>'+'<td width="8%">0</td>'+'<td width="8%">0</td>'+'<td width="8%">0</td>'+'<td width="8%">0</td>'+'<td width="8%">0</td>'+'<td width="8%">0</td>'+'<td width="8%">0</td>'+'<td width="8%">0</td></tr>'
					}
					document.body.innerHTML +='<table  style="width:30%;height:100%;display: block" id="tableSeason" class="table table-condensed">'+
											'<thead><tr><th>Position</th><th>Id</th><th>Team</th><th><abbr title="Played">P</abbr></th><th><abbr title="Won">W</abbr></th><th><abbr title="Drawn">D</abbr></th><th><abbr title="Lost">L</abbr></th><th><abbr title="Goals for">GF</abbr></th><th><abbr title="Goals against">GA</abbr></th><th><abbr title="Goal difference">GD</abbr></th><th><abbr title="Points">Pts</abbr></th></tr></thead>'+
											'<tbody>'+cad+'</tbody>'+
											'</table>';
					orderTable();
				}) .fail(function() {
						$("#errorMessage2").show()
					  })
		}
		// Call the plugin to sort the table
		function orderTable(){
			 $("#tableSeason tbody tr").each(function(){
				$(this)[0].deleteCell(0)
			 })
			 $('#tableSeason').tablesorter( {sortList: [[POINTS-1,DESC_ORDER], [GOAL_DIFFERENCE-1,DESC_ORDER],[GOALS_FOR-1,DESC_ORDER],[NAME-1,ASC_ORDER]]} ); 
			 $('#tableSeason thead th').unbind('click')
			 var count = 1
			 $("#tableSeason tbody tr").each(function(){
			   $(this).prepend("<td>"+count+"</td>");     
				if (count<5)
					document.getElementById('tableSeason').rows[count].style.backgroundColor=CHAMPIONS_LEAGUE;
				else
					if(count<7)
						document.getElementById('tableSeason').rows[count].style.backgroundColor=EUROPE_LEAGUE;
					else
						if(count>17)
							document.getElementById('tableSeason').rows[count].style.backgroundColor=RELEGATION;
						else
							document.getElementById('tableSeason').rows[count].style.backgroundColor=OTHER;
			   count++
			})
		}
		//Process the change of the season and show an empty table ready to start
		function showTable() {
			$("#errorMessage2").hide()
			var x = document.getElementById("selectpicker").value;
			if (!x){
				$("#tableSeason").remove()
				$("#selectpicker option[value='']").attr('selected', true);
				$("#selectpicker option[value='2012']").attr('selected', false);
				$("#errorMessage").show()
				$("#start").hide()
				$("#stop").hide()
				$("#clear").hide()
			}
			else{
				$("#errorMessage").hide()
				$("#stop").hide()
				$("#selectpicker option[value='']").attr('selected', false);
				$("#selectpicker option[value='2012']").attr('selected', true);
				showEmptyTable()
			}
		}