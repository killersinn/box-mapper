<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="description" content="MTG Box Mapper v0.1.1 BETA - Enter rares in booster packs to map the remaining box">
        <title>MTG Box Mapper v0.1.1 BETA - Open boosters and enter rares</title>
        <script src="scripts/jquery-latest.min.js"></script>
        <script type="text/javascript">

            var _gaq = _gaq || [];
            _gaq.push(['_setAccount', 'UA-38991440-1']);
            _gaq.push(['_trackPageview']);

            (function() {
                var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
                ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
                var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
            })();

        </script>
        <script>
            function submitOpenedPacks(){
                result = '';
                columns = ['L', 'M', 'R'];
                for (i=0; i<columns.length; i++){
                    column = columns[i];
                    for (j=1; j<=12; j++){
                        rowId = column;
                        if (j<10){
                            rowId += '0';
                        }
                        rowId += j;
                        selection = $('#'+rowId).val();
                        if (selection != null && selection != undefined && selection.length>0){
                            result += rowId + "-" +selection.replace(/-/g, "") +"\n";
                        }
                    }
                }
                $.ajax({
                    dataType: "json",
                    url: "guess",
                    type: "post",
                    data: "openedPacks=" + result,
                    success: function(JSON){
                        if(JSON.error.length == 0){
                            for(i=0; i<JSON.response.length; i++){
                                result = '';
                                result += JSON.response[i].location + " <font color=\"red\">(";
                                result += JSON.response[i].trackPattern + ")</font><br />";
                                for(j=0; j<JSON.response[i].predictions.length; j++){
                                    result += "<font color=\"blue\">("+JSON.response[i].predictions[j].track;
                                    result += JSON.response[i].predictions[j].queue+")</font> <b>";
                                    result += JSON.response[i].predictions[j].safeName+"</b> <font color=\"red\">[";
                                    result += JSON.response[i].predictions[j].approximatePrice+"]</font><br />";
                                }
                                $('#' + JSON.response[i].location + 'response').html(result);
                            }
                        } else {
                            alert(JSON.error);
                        }
                    },
                    error:function(){
                        alert("failure getting results");
                    } 
                });
            }
        </script>
    </head>
    <body>
        <center>
            <h1>MTG Box Mapper v0.1.1 BETA - Open boosters and enter rares</h1>
            <hr width="90%"/>
            <div align="right">
                <a href="http://www.facebook.com/pages/MTG-Box-Mapping/548842451815313"><div style="min-width:22px; min-height:22px; background-image: url('http://static.ak.fbcdn.net/rsrc.php/v2/y7/x/ox-3tzsRaQW.png');background-repeat: no-repeat; background-position: 0 -66px;height: 22px;width: 22px;"></div></a>
                <a href="https://twitter.com/Box_Mapper"><img width="22px" height="22px" src="https://si0.twimg.com/profile_images/2284174887/zkkmew2x5drntu7z7z9q_normal.png"></a>
            </div>
            <table cellpadding="2" cellspacing="2" border="0" align="center"/>
            <tr align="center"><td colspan="3"><img src="images/box.png" /></td></tr>
            <tr align="center"><td>Left</td><td>Middle</td><td>Right</td></tr>
            <tr align="center">
                <td>&nbsp;1- <input id="L01" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;1- <input id="M01" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;1- <input id="R01" type="text" size="15" maxlength="45" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;2- <input id="L02" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;2- <input id="M02" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;2- <input id="R02" type="text" size="15" maxlength="45" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;3- <input id="L03" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;3- <input id="M03" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;3- <input id="R03" type="text" size="15" maxlength="45" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;4- <input id="L04" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;4- <input id="M04" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;4- <input id="R04" type="text" size="15" maxlength="45" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;5- <input id="L05" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;5- <input id="M05" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;5- <input id="R05" type="text" size="15" maxlength="45" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;6- <input id="L06" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;6- <input id="M06" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;6- <input id="R06" type="text" size="15" maxlength="45" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;7- <input id="L07" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;7- <input id="M07" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;7- <input id="R07" type="text" size="15" maxlength="45" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;8- <input id="L08" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;8- <input id="M08" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;8- <input id="R08" type="text" size="15" maxlength="45" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;9- <input id="L09" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;9- <input id="M09" type="text" size="15" maxlength="45" /></td>
                <td>&nbsp;9- <input id="R09" type="text" size="15" maxlength="45" /></td>
            </tr>
            <tr align="center">
                <td>10- <input id="L10" type="text" size="15" maxlength="45" /></td>
                <td>10- <input id="M10" type="text" size="15" maxlength="45" /></td>
                <td>10- <input id="R10" type="text" size="15" maxlength="45" /></td>
            </tr>
            <tr align="center">
                <td>11- <input id="L11" type="text" size="15" maxlength="45" /></td>
                <td>11- <input id="M11" type="text" size="15" maxlength="45" /></td>
                <td>11- <input id="R11" type="text" size="15" maxlength="45" /></td>
            </tr>
            <tr align="center">
                <td>12- <input id="L12" type="text" size="15" maxlength="45" /></td>
                <td>12- <input id="M12" type="text" size="15" maxlength="45" /></td>
                <td>12- <input id="R12" type="text" size="15" maxlength="45" /></td>
            </tr>
        </table>
        <input type="button" value="Map it!" onclick="submitOpenedPacks();" />
        <hr width="90%"/>
        <table cellpadding="2" cellspacing="2" border="0" align="center" border="1"/>
        <tr align="center"><td colspan="3">Results:</td></tr>
        <tr align="center">
            <td><div id="L1response" style="min-height: 180px;"></div></td>
            <td><div id="M1response" style="min-height: 180px;"></div></td>
            <td><div id="R1response" style="min-height: 180px;"></div></td>
        </tr>
        <tr align="center">
            <td><div id="L2response" style="min-height: 180px;"></div></td>
            <td><div id="M2response" style="min-height: 180px;"></div></td>
            <td><div id="R2response" style="min-height: 180px;"></div></td>
        </tr>
        <tr align="center">
            <td><div id="L3response" style="min-height: 180px;"></div></td>
            <td><div id="M3response" style="min-height: 180px;"></div></td>
            <td><div id="R3response" style="min-height: 180px;"></div></td>
        </tr>
        <tr align="center">
            <td><div id="L4response" style="min-height: 180px;"></div></td>
            <td><div id="M4response" style="min-height: 180px;"></div></td>
            <td><div id="R4response" style="min-height: 180px;"></div></td>
        </tr>
        <tr align="center">
            <td><div id="L5response" style="min-height: 180px;"></div></td>
            <td><div id="M5response" style="min-height: 180px;"></div></td>
            <td><div id="R5response" style="min-height: 180px;"></div></td>
        </tr>
        <tr align="center">
            <td><div id="L6response" style="min-height: 180px;"></div></td>
            <td><div id="M6response" style="min-height: 180px;"></div></td>
            <td><div id="R6response" style="min-height: 180px;"></div></td>
        </tr>
        <tr align="center">
            <td><div id="L7response" style="min-height: 180px;"></div></td>
            <td><div id="M7response" style="min-height: 180px;"></div></td>
            <td><div id="R7response" style="min-height: 180px;"></div></td>
        </tr>
        <tr align="center">
            <td><div id="L8response" style="min-height: 180px;"></div></td>
            <td><div id="M8response" style="min-height: 180px;"></div></td>
            <td><div id="R8response" style="min-height: 180px;"></div></td>
        </tr>
        <tr align="center">
            <td><div id="L9response" style="min-height: 180px;"></div></td>
            <td><div id="M9response" style="min-height: 180px;"></div></td>
            <td><div id="R9response" style="min-height: 180px;"></div></td>
        </tr>
        <tr align="center">
            <td><div id="L10response" style="min-height: 180px;"></div></td>
            <td><div id="M10response" style="min-height: 180px;"></div></td>
            <td><div id="R10response" style="min-height: 180px;"></div></td>
        </tr>
        <tr align="center">
            <td><div id="L11response" style="min-height: 180px;"></div></td>
            <td><div id="M11response" style="min-height: 180px;"></div></td>
            <td><div id="R11response" style="min-height: 180px;"></div></td>
        </tr>
        <tr align="center">
            <td><div id="L12response" style="min-height: 180px;"></div></td>
            <td><div id="M12response" style="min-height: 180px;"></div></td>
            <td><div id="R12response" style="min-height: 180px;"></div></td>
        </tr>
    </table>
</center>
<div align="right"><font color="red" size="2">*Please note that sleeves need to be entered correctly for this page to work. Go to <a href="openGuessPanel">Sleeve Selection</a> page and check your input if this page fails.</font></div>
</body>
</html>
