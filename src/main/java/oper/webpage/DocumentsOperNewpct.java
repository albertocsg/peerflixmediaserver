package oper.webpage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import model.Ficha;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class DocumentsOperNewpct implements IDocumentsOper {
	
	private final String domain = "http://www.newpct.com";

	public String getPageURL(int typeDoc, int page) {
		String url = null;

		switch (typeDoc) {

			default:
			case 1: // BRRip DVDRip
				url = domain + "/include.inc/ajax.php/orderCategory.php?type=todo&leter=&sql=SELECT+DISTINCT+++%09%09%09%09%09%09torrentID%2C+++%09%09%09%09%09%09torrentCategoryID%2C+++%09%09%09%09%09%09torrentCategoryIDR%2C+++%09%09%09%09%09%09torrentImageID%2C+++%09%09%09%09%09%09torrentName%2C+++%09%09%09%09%09%09guid%2C+++%09%09%09%09%09%09torrentShortName%2C++%09%09%09%09%09%09torrentLanguage%2C++%09%09%09%09%09%09torrentSize%2C++%09%09%09%09%09%09calidad+as+calidad_%2C++%09%09%09%09%09%09torrentDescription%2C++%09%09%09%09%09%09torrentViews%2C++%09%09%09%09%09%09rating%2C++%09%09%09%09%09%09n_votos%2C++%09%09%09%09%09%09vistas_hoy%2C++%09%09%09%09%09%09vistas_ayer%2C++%09%09%09%09%09%09vistas_semana%2C++%09%09%09%09%09%09vistas_mes++%09%09%09%09++FROM+torrentsFiles+as+t+WHERE++(torrentStatus+%3D+1+OR+torrentStatus+%3D+2)++AND+(torrentCategoryID+IN+(1537%2C+758%2C+1105%2C+760%2C+1225))++AND+home_active+%3D+0++++ORDER+BY+torrentDateAdded++DESC++LIMIT+0%2C+50&pag="
						+ page + "&tot=&ban=3&cate=1224";
				break;
	
			case 2: // Estrenos
				url = domain + "/include.inc/ajax.php/orderCategory.php?type=todo&leter=&sql=SELECT+DISTINCT+++%09%09%09%09%09%09torrentID%2C+++%09%09%09%09%09%09torrentCategoryID%2C+++%09%09%09%09%09%09torrentCategoryIDR%2C+++%09%09%09%09%09%09torrentImageID%2C+++%09%09%09%09%09%09torrentName%2C+++%09%09%09%09%09%09guid%2C+++%09%09%09%09%09%09torrentShortName%2C++%09%09%09%09%09%09torrentLanguage%2C++%09%09%09%09%09%09torrentSize%2C++%09%09%09%09%09%09calidad+as+calidad_%2C++%09%09%09%09%09%09torrentDescription%2C++%09%09%09%09%09%09torrentViews%2C++%09%09%09%09%09%09rating%2C++%09%09%09%09%09%09n_votos%2C++%09%09%09%09%09%09vistas_hoy%2C++%09%09%09%09%09%09vistas_ayer%2C++%09%09%09%09%09%09vistas_semana%2C++%09%09%09%09%09%09vistas_mes++%09%09%09%09++FROM+torrentsFiles+as+t+WHERE++(torrentStatus+%3D+1+OR+torrentStatus+%3D+2)++AND+(torrentCategoryID+IN+(1231%2C+1165%2C+1230%2C+1232%2C+766%2C+765%2C+761%2C+848%2C+1224))++AND+home_active+%3D+0++++ORDER+BY+torrentDateAdded++DESC++LIMIT+0%2C+50&pag="
						+ page + "&tot=&ban=3&cate=1224";
				break;
	
			case 3: // MicroHD
				url = domain + "/include.inc/ajax.php/orderCategory.php?type=calidad&leter=3&sql=SELECT+DISTINCT+++%09%09%09%09%09%09torrentID%2C+++%09%09%09%09%09%09torrentCategoryID%2C+++%09%09%09%09%09%09torrentCategoryIDR%2C+++%09%09%09%09%09%09torrentImageID%2C+++%09%09%09%09%09%09torrentName%2C+++%09%09%09%09%09%09guid%2C+++%09%09%09%09%09%09torrentShortName%2C++%09%09%09%09%09%09torrentLanguage%2C++%09%09%09%09%09%09torrentSize%2C++%09%09%09%09%09%09calidad+as+calidad_%2C++%09%09%09%09%09%09torrentDescription%2C++%09%09%09%09%09%09torrentViews%2C++%09%09%09%09%09%09rating%2C++%09%09%09%09%09%09n_votos%2C++%09%09%09%09%09%09vistas_hoy%2C++%09%09%09%09%09%09vistas_ayer%2C++%09%09%09%09%09%09vistas_semana%2C++%09%09%09%09%09%09vistas_mes++%09%09%09%09++FROM+torrentsFiles+as+t+WHERE++%28torrentStatus+%3D+1+OR+torrentStatus+%3D+2%29++AND+%28torrentCategoryID+IN+%281029%2C+1028%2C+1774%2C+1027%29%29++AND+home_active+%3D+0+++AND++t.calidad+%3D+%27BluRay+MicroHD%27+++ORDER+BY+torrentDateAdded++DESC++LIMIT+0%2C+50&pag="
						+ page + "&tot=&ban=3&cate=1224";
				break;
	
			case 4: // Series HDTV
				url = domain + "/include.inc/ajax.php/orderCategory.php?type=todo&leter=&sql=SELECT+DISTINCT+++%09%09%09%09%09%09torrentID%2C+++%09%09%09%09%09%09torrentCategoryID%2C+++%09%09%09%09%09%09torrentCategoryIDR%2C+++%09%09%09%09%09%09torrentImageID%2C+++%09%09%09%09%09%09torrentName%2C+++%09%09%09%09%09%09guid%2C+++%09%09%09%09%09%09torrentShortName%2C++%09%09%09%09%09%09torrentLanguage%2C++%09%09%09%09%09%09torrentSize%2C++%09%09%09%09%09%09calidad+as+calidad_%2C++%09%09%09%09%09%09torrentDescription%2C++%09%09%09%09%09%09torrentViews%2C++%09%09%09%09%09%09rating%2C++%09%09%09%09%09%09n_votos%2C++%09%09%09%09%09%09vistas_hoy%2C++%09%09%09%09%09%09vistas_ayer%2C++%09%09%09%09%09%09vistas_semana%2C++%09%09%09%09%09%09vistas_mes++%09%09%09%09++FROM+torrentsFiles+as+t+WHERE++(torrentStatus+%3D+1+OR+torrentStatus+%3D+2)++AND+(torrentCategoryID+IN+(1317%2C+1439%2C+1112%2C+845%2C+1771%2C+1577%2C+1103%2C+1430%2C+1190%2C+850%2C+1153%2C+1318%2C+1154%2C+1823%2C+1159%2C+1314%2C+956%2C+1129%2C+1014%2C+1462%2C+1015%2C+768%2C+1202%2C+1012%2C+922%2C+1707%2C+1448%2C+983%2C+1443%2C+846%2C+800%2C+885%2C+769%2C+1354%2C+1576%2C+1685%2C+959%2C+1535%2C+1217%2C+1372%2C+1592%2C+1331%2C+1792%2C+1361%2C+1661%2C+1208%2C+1750%2C+881%2C+1403%2C+1692%2C+1698%2C+798%2C+1391%2C+1218%2C+1758%2C+1669%2C+1652%2C+1267%2C+1067%2C+1760%2C+1720%2C+925%2C+1626%2C+1768%2C+875%2C+1369%2C+1288%2C+1343%2C+1534%2C+1304%2C+869%2C+1259%2C+1438%2C+1604%2C+805%2C+1169%2C+1678%2C+817%2C+1155%2C+1721%2C+1433%2C+1381%2C+902%2C+840%2C+804%2C+797%2C+1033%2C+1055%2C+1450%2C+1410%2C+1280%2C+1054%2C+1419%2C+1131%2C+1389%2C+1278%2C+1201%2C+1199%2C+1123%2C+1193%2C+1543%2C+1326%2C+1418%2C+1125%2C+1009%2C+941%2C+1004%2C+1120%2C+1558%2C+1327%2C+968%2C+879%2C+1370%2C+1672%2C+1464%2C+1586%2C+919%2C+947%2C+1753%2C+1266%2C+1357%2C+1179%2C+1497%2C+1360%2C+1688%2C+1116%2C+1541%2C+1780%2C+1262%2C+1056%2C+1686%2C+1200%2C+949%2C+971%2C+1160%2C+1648%2C+1532%2C+1076%2C+853%2C+1284%2C+1704%2C+1040%2C+1356%2C+1235%2C+1233%2C+1380%2C+1512%2C+1640%2C+1272%2C+1000%2C+1330%2C+1412%2C+1337%2C+1045%2C+1041%2C+1188%2C+1215%2C+1340%2C+1075%2C+1133%2C+1516%2C+1161%2C+1239%2C+1567%2C+1213%2C+1258%2C+1365%2C+1761%2C+1328%2C+1460%2C+1281%2C+1094%2C+1407%2C+1350%2C+1313%2C+1790%2C+1442%2C+931%2C+1176%2C+1737%2C+1358%2C+1008%2C+1513%2C+1637%2C+882%2C+1099%2C+909%2C+1289%2C+1764%2C+880%2C+976%2C+1150%2C+1011%2C+1252%2C+1741%2C+1248%2C+795%2C+952%2C+1300%2C+1697%2C+843%2C+1616%2C+1117%2C+872%2C+930%2C+1671%2C+1273%2C+937%2C+960%2C+1557%2C+842%2C+1458%2C+1770%2C+1286%2C+1665%2C+912%2C+1431%2C+1044%2C+1607%2C+1393%2C+1023%2C+1613%2C+1734%2C+883%2C+897%2C+1524%2C+865%2C+1219%2C+923%2C+1069%2C+1228%2C+1158%2C+1400%2C+1545%2C+1428%2C+1109%2C+811%2C+1142%2C+1107%2C+868%2C+1523%2C+935%2C+1635%2C+1182%2C+1068%2C+1287%2C+1132%2C+1122%2C+932%2C+1724%2C+1560%2C+1271%2C+1353%2C+1140%2C+1429%2C+1285%2C+1296%2C+1096%2C+1461%2C+996%2C+1177%2C+1295%2C+1812%2C+1638%2C+1291%2C+1212%2C+1364%2C+1130%2C+1591%2C+1324%2C+1345%2C+1173%2C+1349%2C+1712%2C+934%2C+1098%2C+1047%2C+1683%2C+1325%2C+977%2C+989%2C+948%2C+1504%2C+1420%2C+1396%2C+1636%2C+860%2C+1525%2C+1690%2C+796%2C+1623%2C+1717%2C+1590%2C+1253%2C+1795%2C+1452%2C+1444%2C+910%2C+1559%2C+905%2C+1642%2C+1544%2C+1216%2C+1745%2C+772%2C+1561%2C+1414%2C+867%2C+1723%2C+1674%2C+1102%2C+1322%2C+997%2C+1628%2C+774%2C+1211%2C+1081%2C+1209%2C+1383%2C+1355%2C+1387%2C+1292%2C+1373%2C+1086%2C+1087%2C+1752%2C+1210%2C+943%2C+926%2C+1747%2C+1185%2C+1465%2C+1180%2C+1446%2C+1675%2C+1298%2C+945%2C+1022%2C+1457%2C+1175%2C+1174%2C+1659%2C+1436%2C+1802%2C+1078%2C+1257%2C+1254%2C+1196%2C+1515%2C+808%2C+1522%2C+1197%2C+1468%2C+1363%2C+895%2C+1622%2C+1316%2C+1778%2C+825%2C+1376%2C+1007%2C+1183%2C+855%2C+979%2C+1265%2C+1368%2C+1089%2C+1162%2C+1422%2C+1058%2C+1189%2C+1482%2C+1632%2C+857%2C+1293%2C+1736%2C+1762%2C+858%2C+1445%2C+1423%2C+1388%2C+1338%2C+1134%2C+1709%2C+1037%2C+831%2C+1194%2C+1052%2C+946%2C+1206%2C+1838%2C+1743%2C+1589%2C+1255%2C+1767%2C+1379%2C+815%2C+892%2C+810%2C+920%2C+1305%2C+1053%2C+1494%2C+1739%2C+1480%2C+1506%2C+1344%2C+813%2C+1079%2C+1666%2C+1570%2C+1633%2C+927%2C+1610%2C+1413%2C+1695%2C+1002%2C+1119%2C+1731%2C+1630%2C+876%2C+793%2C+1362%2C+1335%2C+1392%2C+1718%2C+1039%2C+1195%2C+936%2C+1375%2C+1371%2C+1242%2C+1191%2C+984%2C+1310%2C+1205%2C+1377%2C+1417%2C+962%2C+1378%2C+906%2C+1507%2C+1245%2C+1765%2C+1726%2C+1303%2C+1716%2C+1627%2C+1111%2C+1104%2C+915%2C+1797%2C+1386%2C+975%2C+803%2C+1164%2C+1124%2C+799%2C+951%2C+1332%2C+918%2C+856%2C+1395%2C+1794%2C+1715%2C+1198%2C+1294%2C+1108%2C+1538%2C+1641%2C+1319%2C+887%2C+871%2C+1282%2C+809%2C+1084%2C+884%2C+1347%2C+1141%2C+966%2C+877%2C+1710%2C+1034%2C+771%2C+1030%2C+773%2C+901%2C+1247%2C+1051%2C+1106%2C+1384%2C+1440%2C+1609%2C+1664%2C+1426%2C+1311%2C+1673%2C+1163%2C+1351%2C+1575%2C+770%2C+1432%2C+957%2C+1382%2C+1409%2C+1113%2C+953%2C+1421%2C+1663%2C+1306%2C+1312%2C+1032%2C+1397%2C+852%2C+1425%2C+1151%2C+1447%2C+1405%2C+1031%2C+1502%2C+1186%2C+1178%2C+1776%2C+1705%2C+1059%2C+1644%2C+1297%2C+851%2C+1348%2C+1301%2C+1756%2C+1456%2C+1569%2C+1101%2C+1394%2C+1509%2C+1385%2C+1057%2C+1115%2C+891%2C+1244%2C+1441%2C+1234%2C+1342%2C+1308%2C+1077%2C+1181%2C+1227%2C+1437%2C+1157%2C+1703%2C+1016%2C+1568%2C+1329%2C+1320%2C+1539%2C+1742%2C+1682%2C+1035%2C+964%2C+1553%2C+1152%2C+1072%2C+1367%2C+1519%2C+1036%2C+1334%2C+1454%2C+993%2C+1793%2C+981%2C+1126%2C+1775%2C+1241%2C+1572%2C+1043%2C+1755%2C+1156%2C+1097%2C+1700%2C+844%2C+1517%2C+1307%2C+839%2C+1263%2C+1336%2C+1088%2C+1359%2C+1279%2C+1404%2C+1653%2C+1536%2C+889%2C+1566%2C+987%2C+801%2C+870%2C+917%2C+1135%2C+816%2C+807%2C+1264%2C+1453%2C+1459%2C+1467%2C+1751%2C+1302%2C+847%2C+967%2C+1309%2C+1137%2C+1434%2C+834%2C+1773%2C+1427%2C+1100%2C+1463%2C+1510%2C+1171%2C+1680%2C+1214%2C+1092%2C+1713%2C+1408%2C+1667%2C+1366%2C+1435%2C+1192%2C+1615%2C+1050%2C+1402%2C+1346%2C+1542%2C+1323%2C+1521%2C+1564%2C+1617%2C+1416%2C+1415%2C+1424%2C+1251%2C+1621%2C+1085%2C+1398%2C+1399%2C+1796%2C+1798%2C+1166%2C+1719%2C+1256%2C+1722%2C+1735%2C+1777%2C+1670%2C+1562%2C+980%2C+988%2C+1274%2C+1299%2C+1660%2C+1766%2C+1114%2C+1466%2C+1013%2C+1658%2C+1339%2C+878%2C+1333%2C+1138%2C+1283%2C+1565%2C+1401%2C+1573%2C+1010%2C+1321%2C+1540%2C+1341%2C+1455%2C+1005%2C+1625%2C+1499%2C+1728%2C+1808%2C+1243%2C+1207%2C+1290%2C+1608%2C+1128%2C+1725%2C+907%2C+806%2C+1136%2C+1738%2C+914%2C+1110%2C+1601%2C+1587%2C+1411%2C+1352%2C+1187%2C+1588%2C+1229%2C+1651%2C+1249%2C+1315%2C+1093%2C+992%2C+1593%2C+1533%2C+1749%2C+1645%2C+1017%2C+991%2C+1204%2C+802%2C+1655%2C+1679%2C+1276%2C+1270%2C+1496%2C+859%2C+1449%2C+963%2C+1563%2C+911%2C+1501%2C+1168%2C+1184%2C+1451%2C+767))++AND+home_active+%3D+0++++ORDER+BY+torrentDateAdded++DESC++LIMIT+0%2C+50&pag="
						+ page + "&tot=&ban=3&cate=1469";
				break;
	
			case 5: // Series HD
				url = domain + "/include.inc/ajax.php/orderCategory.php?type=todo&leter=&sql=SELECT+DISTINCT+++%09%09%09%09%09%09torrentID%2C+++%09%09%09%09%09%09torrentCategoryID%2C+++%09%09%09%09%09%09torrentCategoryIDR%2C+++%09%09%09%09%09%09torrentImageID%2C+++%09%09%09%09%09%09torrentName%2C+++%09%09%09%09%09%09guid%2C+++%09%09%09%09%09%09torrentShortName%2C++%09%09%09%09%09%09torrentLanguage%2C++%09%09%09%09%09%09torrentSize%2C++%09%09%09%09%09%09calidad+as+calidad_%2C++%09%09%09%09%09%09torrentDescription%2C++%09%09%09%09%09%09torrentViews%2C++%09%09%09%09%09%09rating%2C++%09%09%09%09%09%09n_votos%2C++%09%09%09%09%09%09vistas_hoy%2C++%09%09%09%09%09%09vistas_ayer%2C++%09%09%09%09%09%09vistas_semana%2C++%09%09%09%09%09%09vistas_mes++%09%09%09%09++FROM+torrentsFiles+as+t+WHERE++%28torrentStatus+%3D+1+OR+torrentStatus+%3D+2%29++AND+%28torrentCategoryID+IN+%281772%2C+1582%2C+1473%2C+1708%2C+1474%2C+1603%2C+1596%2C+1611%2C+1693%2C+1699%2C+1759%2C+1769%2C+1598%2C+1514%2C+1605%2C+1585%2C+1472%2C+1754%2C+1689%2C+1791%2C+1475%2C+1687%2C+1649%2C+1643%2C+1476%2C+1486%2C+1618%2C+1490%2C+1657%2C+1606%2C+1498%2C+1493%2C+1639%2C+1488%2C+1684%2C+1505%2C+1691%2C+1495%2C+1624%2C+1470%2C+1746%2C+1676%2C+1629%2C+1511%2C+1748%2C+1677%2C+1484%2C+1485%2C+1580%2C+1763%2C+1744%2C+1481%2C+1520%2C+1696%2C+1492%2C+1508%2C+1727%2C+1711%2C+1579%2C+1489%2C+1706%2C+1757%2C+1487%2C+1583%2C+1477%2C+1701%2C+1518%2C+1526%2C+1654%2C+1694%2C+1491%2C+1478%2C+1681%2C+1714%2C+1668%2C+1619%2C+1581%2C+1479%2C+1483%2C+1500%2C+1729%2C+1809%2C+1584%2C+1740%2C+1602%2C+1646%2C+1656%2C+1471%2C+1469%29%29++AND+home_active+%3D+0++++ORDER+BY+torrentDateAdded++DESC++LIMIT+0%2C+50&pag="
						+ page + "&tot=&ban=3&cate=1469";
				break;
				
			case 6: // Otros
				url = domain + "/include.inc/ajax.php/orderCategory.php?type=todo&leter=&sql=SELECT+DISTINCT+++%09%09%09%09%09%09torrentID%2C+++%09%09%09%09%09%09torrentCategoryID%2C+++%09%09%09%09%09%09torrentCategoryIDR%2C+++%09%09%09%09%09%09torrentImageID%2C+++%09%09%09%09%09%09torrentName%2C+++%09%09%09%09%09%09guid%2C+++%09%09%09%09%09%09torrentShortName%2C++%09%09%09%09%09%09torrentLanguage%2C++%09%09%09%09%09%09torrentSize%2C++%09%09%09%09%09%09calidad+as+calidad_%2C++%09%09%09%09%09%09torrentDescription%2C++%09%09%09%09%09%09torrentViews%2C++%09%09%09%09%09%09rating%2C++%09%09%09%09%09%09n_votos%2C++%09%09%09%09%09%09vistas_hoy%2C++%09%09%09%09%09%09vistas_ayer%2C++%09%09%09%09%09%09vistas_semana%2C++%09%09%09%09%09%09vistas_mes++%09%09%09%09++FROM+torrentsFiles+as+t+WHERE++(torrentStatus+%3D+1+OR+torrentStatus+%3D+2)++AND+(torrentCategoryID+IN+(830%2C+827))++AND+home_active+%3D+0++++ORDER+BY+torrentDateAdded++DESC++LIMIT+0%2C+50&pag="
						+ page + "&tot=&ban=3&cate=827";
				break;
				
			case 7: // Series V.O.
				url = domain + "/include.inc/ajax.php/orderCategory.php?type=todo&leter=&sql=SELECT%20DISTINCT%20%20%20%09%09%09%09%09%09torrentID%2C%20%20%20%09%09%09%09%09%09torrentCategoryID%2C%20%20%20%09%09%09%09%09%09torrentCategoryIDR%2C%20%20%20%09%09%09%09%09%09torrentImageID%2C%20%20%20%09%09%09%09%09%09torrentName%2C%20%20%20%09%09%09%09%09%09guid%2C%20%20%20%09%09%09%09%09%09torrentShortName%2C%20%20%09%09%09%09%09%09torrentLanguage%2C%20%20%09%09%09%09%09%09torrentSize%2C%20%20%09%09%09%09%09%09calidad%20as%20calidad_%2C%20%20%09%09%09%09%09%09torrentDescription%2C%20%20%09%09%09%09%09%09torrentViews%2C%20%20%09%09%09%09%09%09rating%2C%20%20%09%09%09%09%09%09n_votos%2C%20%20%09%09%09%09%09%09vistas_hoy%2C%20%20%09%09%09%09%09%09vistas_ayer%2C%20%20%09%09%09%09%09%09vistas_semana%2C%20%20%09%09%09%09%09%09vistas_mes%20%20%09%09%09%09%20%20FROM%20torrentsFiles%20as%20t%20WHERE%20%20(torrentStatus%20%3D%201%20OR%20torrentStatus%20%3D%202)%20%20AND%20(torrentCategoryID%20IN%20(921%2C%201006%2C%201850%2C%201261%2C%201807%2C%201835%2C%201019%2C%20900%2C%20863%2C%201038%2C%201806%2C%20990%2C%201024%2C%20837%2C%201817%2C%201801%2C%201277%2C%201804%2C%201861%2C%20861%2C%201847%2C%20854%2C%20862%2C%201503%2C%201063%2C%201842%2C%201090%2C%20776%2C%201837%2C%201827%2C%201118%2C%201021%2C%20894%2C%20818%2C%201221%2C%201839%2C%20777%2C%201826%2C%20933%2C%20835%2C%20866%2C%20864%2C%201139%2C%201853%2C%201260%2C%20836%2C%201819%2C%201020%2C%201841%2C%201803%2C%201810%2C%201836%2C%201222%2C%201275%2C%201003%2C%201824%2C%20961%2C%20986%2C%201799%2C%201840%2C%201800%2C%201825%2C%201240%2C%20908%2C%20775))%20%20AND%20home_active%20%3D%200%20%20%20%20ORDER%20BY%20torrentDateAdded%20%20DESC%20%20LIMIT%200%2C%2050&pag="
						+ page + "&tot=&ban=3&cate=775";
				break;

			case 8: // Documentales
				url = domain + "/include.inc/ajax.php/orderCategory.php?type=todo&leter=&sql=SELECT+DISTINCT+++%09%09%09%09%09%09torrentID%2C+++%09%09%09%09%09%09torrentCategoryID%2C+++%09%09%09%09%09%09torrentCategoryIDR%2C+++%09%09%09%09%09%09torrentImageID%2C+++%09%09%09%09%09%09torrentName%2C+++%09%09%09%09%09%09guid%2C+++%09%09%09%09%09%09torrentShortName%2C++%09%09%09%09%09%09torrentLanguage%2C++%09%09%09%09%09%09torrentSize%2C++%09%09%09%09%09%09calidad+as+calidad_%2C++%09%09%09%09%09%09torrentDescription%2C++%09%09%09%09%09%09torrentViews%2C++%09%09%09%09%09%09rating%2C++%09%09%09%09%09%09n_votos%2C++%09%09%09%09%09%09vistas_hoy%2C++%09%09%09%09%09%09vistas_ayer%2C++%09%09%09%09%09%09vistas_semana%2C++%09%09%09%09%09%09vistas_mes++%09%09%09%09++FROM+torrentsFiles+as+t+WHERE++(torrentStatus+%3D+1+OR+torrentStatus+%3D+2)++AND+(torrentCategoryID+IN+(782%2C+1143%2C+781%2C+780))++AND+home_active+%3D+0++++ORDER+BY+torrentDateAdded++DESC++LIMIT+0%2C+50&pag="
						+ page + "&tot=&ban=3&cate=780";
				break;
				
		}

		return getPage(url);

	}

	public List<Ficha> processPage(String page) {
		Document doc = Jsoup.parse(page);
		Elements elements = doc.getElementsByTag("li");
		ArrayList<Ficha> fichas = new ArrayList<Ficha>();
		String nombre = null;
		for (Element e : elements) {
			Ficha ficha = new Ficha();
			nombre = e.getElementsByClass("cover").get(0).text();
			ficha.setNombre(nombre);
			ficha.setImagen(e.getElementsByTag("img").get(0).attr("src"));
			ficha.setUrl(e.getElementsByTag("a").get(0).attr("href"));
			fichas.add(ficha);
		}
		return fichas;
	}

	public void getTorrent(Ficha ficha) {
		if (ficha == null) {
			return;
		}

		String page = getPage(ficha.getUrl());

		Document doc = Jsoup.parse(page);
		String magnet = doc.getElementById("content-torrent").getElementsByTag("a").get(0).attr("href");
		String details = doc.getElementsByClass("descripcion_top").get(0).html();
		details += "<br>" + doc.getElementsByClass("sinopsis").get(0).html();

		// Prepare the correct url of the torrent.
		magnet = domain + "/" + magnet.substring(magnet.indexOf("torrents"));
		
		ficha.setTorrent(magnet);
		ficha.setDetails(details);
	}

	public String getPageSearch(String search, int page) {
		// TODO
		return "";
	}

	/**
	 * Get the html of the given page.
	 * 
	 * @param page URL
	 * @return String with the html
	 */
	private String getPage(String page) {
		StringBuilder document = new StringBuilder();
		try {

			URL url = new URL(page);

			URLConnection conn = url.openConnection();
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "windows-1252"));

			String linea;

			while ((linea = entrada.readLine()) != null) {
				document.append(linea);
			}

			entrada.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return document.toString();

	}

}
