<html><head><meta http-equiv=Content-Type content='text/html; charset=UTF-8'><style>body,table,tr,td {  font-family:"Calibri","sans-serif";   font-size:11pt;  } table {    vertical-align:center;    border-collapse:collapse;    border:solid #B2B2B2 1.0pt;  } .th {   font-weight: bold;    color:white;    background:#254061;    text-align:center;    padding:4px 10px;  } .label {   font-weight: bold;    padding-left:2px;  } .data {   padding:2px 7px;  } .tek {   background:#EFEFF1;    line-height:125%;  } .cift {   background:white;    line-height:125%;  } td {    border:solid #B2B2B2 0.5pt;    border-collapse:collapse;  } .ilkKolonBold {    font-weight:bold;  } </style></head><body>
<#if msg.useUserName>
${msg.mailUserName},
</#if>
<br>
<#if msg.information??>
${msg.information}<br><br>
</#if>

<#if msg.dataList??>

<table border=0 cellspacing=0 cellpadding=0>
<thead>
<tr>
<#assign head = msg.dataList[0]>
<#list head?keys as key>
<td class="th">${key?upper_case?replace("_"," ")}</td>
</#list>
</tr>
</thead>
<tbody>
<#list msg.dataList as row>
<tr class="${row?item_cycle('cift', 'tek')}">
<#list row?keys as key>
<td class="data ilkKolonBold">
<#if row[key]?is_number>
${row[key]?string["0.######"]}
<#else>
${row[key]?string?trim}
</#if>
</td>
</#list>
</tr>
</#list>
</tbody>
</table>
<br>

</#if>

<#if msg.approveLink??>
<table cellspacing="0" cellpadding="0">
<tr><td align="center" width="80" height="20" bgcolor="#254061" style="-webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; color: #ffffff; display: block;">
<a href="${msg.approveLink}" style="font-size:12px; font-weight: bold; font-family: Helvetica, Arial, sans-serif; text-decoration: none; line-height:30px; width:50%; display:inline-block"><span style="color: #FFFFFF">${approve_title}</span></a>
</td>
<td width="20" height="20"></td>

<td align="center" width="120" height="20" bgcolor="#254061" style="-webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; color: #ffffff; display: block;">
<a href="${msg.rejectLink}" style="font-size:12px; font-weight: bold; font-family: Helvetica, Arial, sans-serif; text-decoration: none; line-height:30px; width:50%; display:inline-block"><span style="color: #FFFFFF">${reject_title}</span></a>
</td></tr>
</table>
<br>
</#if>

<#if msg.useMailFooter>
<#if msg.systemLink??>
${system_link_prefix}<a href="${msg.systemLink}">${msg.systemName}</a>${system_link_suffix}<br><br>
</#if>
${do_not_respond}
</#if>
<br></body></html>