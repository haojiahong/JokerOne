package cn.itcast.oa.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.itcast.oa.model.SortParam;
import cn.itcast.oa.model.SortParamList;

public class JpqlUtil {
	public static String deleteOuterOrderBy(String jpql) throws Exception {
		Pattern pattern = Pattern.compile("[)\\s]ORDER\\s+BY[\\s(]", 2);
		Matcher matcher = pattern.matcher(jpql);
		int start = -1;
		int end = -1;
		boolean hasOrder = false;
		while (matcher.find()) {
			start = matcher.start();
			end = matcher.end();
		}
		if (start > 0) {
			boolean hasQuote = false;
			for (int i = end + 1; i < jpql.length(); i++) {
				if (jpql.charAt(i) != '\'')
					continue;
				hasQuote = true;
				break;
			}

			if (!hasQuote) {
				int leftB = 0;
				int rightB = 0;
				for (int j = end - 1; j < jpql.length(); j++) {
					if (jpql.charAt(j) == '(')
						leftB++;
					if (jpql.charAt(j) == ')')
						rightB++;
				}

				if (leftB == rightB)
					hasOrder = true;
			}
		}
		if (hasOrder)
			jpql = jpql.substring(0, start);
		return jpql;
	}

	//增加排序
	public static String addSortParam(String jpql, SortParamList sortParams)
			throws Exception {
		if (sortParams != null && sortParams.getParams().size() > 0) {
			List list = sortParams.getParams();
			for (int i = 0; i < list.size(); i++) {
				SortParam param = (SortParam) list.get(i);
				if (param.getSortProperty().contains(".")){
					throw new Exception("列表排序发生错误。");
				}
				if (i == 0 && hasOrderBy(jpql) < 0)
					jpql = (new StringBuilder(String.valueOf(jpql))).append(
							" ORDER BY ").toString();
				else
					jpql = (new StringBuilder(String.valueOf(jpql))).append(
							" , ").toString();
				jpql = (new StringBuilder(String.valueOf(jpql)))
						.append(param.getAlias() != null
								&& !"".equals(param.getAlias().trim()) ? (new StringBuilder(
								String.valueOf(param.getAlias()))).append(".")
								.toString() : "")
						.append(param.getSortProperty()).append(" ")
						.append(param.getSortType()).toString();
			}

		}
		return jpql;
	}

	private static int hasOrderBy(String jpql) {
		Pattern pattern = Pattern.compile("[)\\s]ORDER\\s+BY[\\s(]", 2);
		Matcher matcher = pattern.matcher(jpql);
		int start = -1;
		int end;
		for (end = -1; matcher.find(); end = matcher.end())
			start = matcher.start();

		if (start >= 0 && !checkHasQuoteAfter(end - 1, jpql))
			return start;
		else
			return -1;
	}

	private static boolean checkHasQuoteAfter(int beginIndex, String jpql) {
		char charArr[] = jpql.toCharArray();
		for (int i = beginIndex; i < charArr.length; i++)
			if (charArr[i] == '\'')
				return true;

		return false;
	}

}
