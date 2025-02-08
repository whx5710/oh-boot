//package com.finn.framework.datasource.interceptor;
//
//import com.finn.core.datasource.config.SysDataSourceProperties;
//import com.finn.core.common.constant.Constant;
//import com.finn.core.datasource.utils.DynamicDataSourceHolder;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
///**
// * 数据源过滤
// *
// * @author 王小费 whx5710@qq.com
// * @since 1.0.0 2024-03-24
// */
//@Component
//public class DataSourceFilter extends OncePerRequestFilter {
//
//    private final Logger log = LoggerFactory.getLogger(DataSourceFilter.class);
//
//    private final SysDataSourceProperties sysDataSourceProperties;
//
//    public DataSourceFilter(SysDataSourceProperties sysDataSourceProperties){
//        this.sysDataSourceProperties = sysDataSourceProperties;
//    }
//
//    private static final String DATA_SOURCE_HEADER = "X-Data-Source"; //Header 名称为 "X-Data-Source"
//
//    /**
//     * 数据源过滤;根据URI请求和header中的参数设置数据源
//     * @param request
//     * @param response
//     * @param filterChain
//     * @throws ServletException
//     * @throws IOException
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if(sysDataSourceProperties.getEnable()){
//            String dataSourceCode = request.getHeader(DATA_SOURCE_HEADER);
//            if(!ObjectUtils.isEmpty(dataSourceCode)){
//                try {
//                    log.debug("切换数据源!{}", dataSourceCode);
//                    DynamicDataSourceHolder.setDynamicDataSourceKey(dataSourceCode);
//                    filterChain.doFilter(request, response);
//                }finally {
//                    // 清除数据源标识
//                    log.debug("清除数据源!{}", dataSourceCode);
//                    DynamicDataSourceHolder.removeDynamicDataSourceKey();
//                }
//            } else if (filterURI(request.getRequestURI())) {
//                try {
//                    log.debug("使用系统数据源!{}", Constant.SYS_DB);
//                    DynamicDataSourceHolder.setDynamicDataSourceKey(Constant.SYS_DB);
//                    filterChain.doFilter(request, response);
//                }finally {
//                    // 清除数据源标识
//                    log.debug("清除系统数据源!{}", Constant.SYS_DB);
//                    DynamicDataSourceHolder.removeDynamicDataSourceKey();
//                }
//            } else {
//                filterChain.doFilter(request, response);
//            }
//        }else{
//            filterChain.doFilter(request, response);
//        }
//    }
//
//    /**
//     * 匹配请求路径
//     * @param uri
//     * @return
//     */
//    private Boolean filterURI(String uri){
//        for(String regex:sysDataSourceProperties.getPrefixUris()){
//            if(uri.startsWith(regex)){
//                return true;
//            }
//        }
//        return false;
//    }
//}
