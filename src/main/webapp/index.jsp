<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="description" content="MTG Box Mapper v0.1.1 BETA - Select sleeves to start box mapping.">
        <title>MTG Box Mapper v0.1.1 BETA - Sleeve Selection</title>
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
            function submitSleeveInput(){
                var result = '';
                var columns = ['L', 'M', 'R'];
                for (i=0; i<columns.length; i++){
                    column = columns[i];
                    for (j=1; j<=12; j++){
                        rowId = column;
                        if (j<10){
                            rowId += '0';
                        }
                        rowId += j;
                        selection = $('#'+rowId).val();
                        if (selection == null || selection == undefined){
                            alert(rowId + ' is empty');
                            return false;
                        }
                        result += selection;
                    }
                    if (i < columns.length-1){
                        result += '-';
                    }
                }
                result = result.replace(/[^1-5\-]/g, '');
                if(result.length != 38){
                    result = $('#L-ALL').val()+'-'+$('#M-ALL').val()+'-'+$('#R-ALL').val();
                    result = result.replace(/[^1-5\-]/g, '');
                    if(result.length != 38){
                        alert('Wrong input. Please check all numbers before proceeding');
                        return false;
                    }
                }
                $('#sleeveInput').val(result);
                $('#sleeveSelection').submit();
            }
        </script>
    </head>
    <body>
        <center>
            <h1>MTG Box Mapper v0.1.1 BETA</h1>
            <hr width="90%"/>
            <div align="right">
                <a href="http://www.facebook.com/pages/MTG-Box-Mapping/548842451815313"><div style="min-width:22px; min-height:22px; background-image: url('http://static.ak.fbcdn.net/rsrc.php/v2/y7/x/ox-3tzsRaQW.png');background-repeat: no-repeat; background-position: 0 -66px;height: 22px;width: 22px;"></div></a>
                <a href="https://twitter.com/Box_Mapper"><img width="22px" height="22px" src="https://si0.twimg.com/profile_images/2284174887/zkkmew2x5drntu7z7z9q_normal.png"></a>
            </div>
            <table cellpadding="2" cellspacing="2" border="0" align="center"/>
            <tr align="center"><td colspan="3"><img src="images/box.png" /></td></tr>
            <tr align="center">
                <td colspan="3">
                    <strong>
                        <table cellpadding="0" cellspacing="0" border="0" align="center"/>
                        <tr align="center"><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td></tr>
                        <tr align="center"><td colspan="5"><img src="images/boosters.png" /></td></tr>
                        </table>
                    </strong>
                </td>
            </tr>
            <tr align="center"><td>Left</td><td>Middle</td><td>Right</td></tr>
            <tr align="center">
                <td>&nbsp;1- <input id="L01" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;1- <input id="M01" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;1- <input id="R01" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;2- <input id="L02" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;2- <input id="M02" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;2- <input id="R02" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;3- <input id="L03" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;3- <input id="M03" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;3- <input id="R03" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;4- <input id="L04" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;4- <input id="M04" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;4- <input id="R04" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;5- <input id="L05" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;5- <input id="M05" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;5- <input id="R05" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;6- <input id="L06" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;6- <input id="M06" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;6- <input id="R06" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;7- <input id="L07" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;7- <input id="M07" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;7- <input id="R07" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;8- <input id="L08" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;8- <input id="M08" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;8- <input id="R08" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td>&nbsp;9- <input id="L09" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;9- <input id="M09" type="text" size="1" maxlength="1" /></td>
                <td>&nbsp;9- <input id="R09" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td>10- <input id="L10" type="text" size="1" maxlength="1" /></td>
                <td>10- <input id="M10" type="text" size="1" maxlength="1" /></td>
                <td>10- <input id="R10" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td>11- <input id="L11" type="text" size="1" maxlength="1" /></td>
                <td>11- <input id="M11" type="text" size="1" maxlength="1" /></td>
                <td>11- <input id="R11" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td>12- <input id="L12" type="text" size="1" maxlength="1" /></td>
                <td>12- <input id="M12" type="text" size="1" maxlength="1" /></td>
                <td>12- <input id="R12" type="text" size="1" maxlength="1" /></td>
            </tr>
            <tr align="center">
                <td colspan="3">or<br />write down all at once</td>
            </tr>
            <tr align="center">
                <td colspan="3"><input id="L-ALL" type="text" size="12" maxlength="12" />-<input id="M-ALL" type="text" size="12" maxlength="12" />-<input id="R-ALL" type="text" size="12" maxlength="12" /></td>
            </tr>
        </table>
        <form id="sleeveSelection" action="openGuessPanel" method="POST">
            <input id="sleeveInput" type="hidden" name="sleeveInput" value="" />
        </form>
        <input type="button" value="Go!" onclick="submitSleeveInput();" /> <br />
        or <br />
        Click <a href="openGuessPanel?sleeveInput=413524135241-524135241352-135241352413">here</a> if you ordered it manually
    </center>
    <div align="right"><font color="red" size="2">I've already done this for this box, go to <a href="openGuessPanel">Card input</a></font></div>
</body>
</html>
