import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class T {

	public static void main(String[] args) {
		String con = "            	<a href=\"javascript:void(0);\" class=\"paging-currentPage\">1</a><a href=\"http://www.vboly.com/list_0_0_2_99.html\" titel=\"查看2页\">2</a><a href=\"http://www.vboly.com/list_0_0_3_99.html\" titel=\"查看3页\">3</a><a href=\"http://www.vboly.com/list_0_0_4_99.html\" titel=\"查看4页\">4</a><a href=\"http://www.vboly.com/list_0_0_5_99.html\" titel=\"查看5页\">5</a><a href=\"http://www.vboly.com/list_0_0_6_99.html\" titel=\"查看6页\">6</a><a href=\"http://www.vboly.com/list_0_0_7_99.html\" titel=\"查看7页\">7</a><a href=\"http://www.vboly.com/list_0_0_8_99.html\" titel=\"查看8页\">8</a><span>...</span><a href=\"http://www.vboly.com/list_0_0_52_99.html\" titel=\"查看52页\">52</a><a href=\"http://www.vboly.com/list_0_0_2_99.html\" titel=\"查看2页\">下一页</a><span>共 52 页</span>                                <form id=\"page_form\" action=\"\">";
		Pattern p = Pattern.compile("共 (\\d+) 页");
		Matcher m = p.matcher(con);
		String group = "";
		while (m.find()) {
			group = m.group(1);
		}
		System.out.println(group);
//		String regex = "<(\\w+)>(\\w+)</(\\w+)>";
//		Pattern pattern = Pattern.compile(regex);
//		String input = "<name>Bill</name><salary>50000</salary><title>GM</title>";
//		Matcher matcher = pattern.matcher(input);
//		// while(matcher.find()){
//		System.out.println(matcher.group(2));
//		// }
	}
}
