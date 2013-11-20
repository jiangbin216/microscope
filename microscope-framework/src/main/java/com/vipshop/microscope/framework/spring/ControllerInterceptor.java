package com.vipshop.microscope.framework.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vipshop.microscope.framework.span.SecondaryCategory;
import com.vipshop.microscope.trace.HTTPHeader;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class ControllerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String traceId = request.getHeader(HTTPHeader.X_B3_TRACE_ID);
		String spanId = request.getHeader(HTTPHeader.X_B3_SPAN_ID);

		String name = SecondaryCategory.buildName(request, handler);
		
		Tracer.clientSend(traceId, spanId, name, Category.ACTION);
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		Tracer.clientReceive();
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

}
