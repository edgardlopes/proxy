package com.example.filters.post

import com.netflix.util.Pair
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.springframework.cloud.netflix.zuul.filters.Route
import org.springframework.cloud.netflix.zuul.filters.RouteLocator
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.util.UrlPathHelper

class LocationHeaderRewritingFilter extends ZuulFilter {
    final UrlPathHelper urlPathHelper = new UrlPathHelper()
    final RouteLocator routeLocator

    LocationHeaderRewritingFilter(RouteLocator routeLocator) {
        this.routeLocator = routeLocator
    }

    @Override
    String filterType() { "post" }

    @Override
    int filterOrder() { 100 }

    @Override
    boolean shouldFilter() {
        locationHeader(RequestContext.getCurrentContext()) != null
    }

    @Override
    Object run() {
        RequestContext ctx = RequestContext.getCurrentContext()
        Route route = routeLocator.getMatchingRoute(urlPathHelper.getPathWithinApplication(ctx.getRequest()));
        if (route != null) {
            Pair<String, String> lh = locationHeader(ctx)
            lh.second = lh.second().replace(
                route.getLocation(),
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString())
        }
    }

    private locationHeader(RequestContext ctx) {
        ctx.getZuulResponseHeaders().find { "Location".equals(it.first()) }
    }
}
