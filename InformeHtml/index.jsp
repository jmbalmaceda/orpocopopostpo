<%@ page session="false"%>
<%@ include file="fragments/i18n_includes.jsp" %>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="fragments/commons.jsp" />
</head>

<script src="${pageContext.request.contextPath}/js/highcharts/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/js/highcharts/highcharts-3d.js"></script>
<!-- <script src="${pageContext.request.contextPath}/js/highcharts/exporting.js"></script>  -->
<script type="text/javascript">
$(function () {

    // Give the points a 3D feel by adding a radial gradient
    Highcharts.getOptions().colors = $.map(Highcharts.getOptions().colors, function (color) {
        return {
            radialGradient: {
                cx: 0.4,
                cy: 0.3,
                r: 0.5
            },
            stops: [
                [0, color],
                [1, Highcharts.Color(color).brighten(-0.2).get('rgb')]
            ]
        };
    });

    // Set up the chart
    var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'container',
            margin: 100,
            type: 'scatter',
            options3d: {
                enabled: true,
                alpha: 10,
                beta: 30,
                depth: 250,
                viewDistance: 5,
                fitToPlot: false,
                frame: {
                    bottom: { size: 1, color: 'rgba(0,0,0,0.02)' },
                    back: { size: 1, color: 'rgba(0,0,0,0.04)' },
                    side: { size: 1, color: 'rgba(0,0,0,0.06)' }
                }
            }
        },
        title: {
            text: 'Draggable box'
        },
        subtitle: {
            text: 'Click and drag the plot area to rotate in space'
        },
        plotOptions: {
            scatter: {
                width: 10,
                height: 10,
                depth: 10
            }
        },
        yAxis: {
            min: 0,
            max: 10,
            title: null
        },
        xAxis: {
            min: 0,
            max: 10,
            gridLineWidth: 1
        },
        zAxis: {
            min: 0,
            max: 10,
            showFirstLabel: false
        },
        legend: {
            enabled: true
        },
        series: ${series}
    });


    // Add mouse events for rotation
    $(chart.container).bind('mousedown.hc touchstart.hc', function (eStart) {
        eStart = chart.pointer.normalize(eStart);

        var posX = eStart.pageX,
            posY = eStart.pageY,
            alpha = chart.options.chart.options3d.alpha,
            beta = chart.options.chart.options3d.beta,
            newAlpha,
            newBeta,
            sensitivity = 5; // lower is more sensitive

        $(document).bind({
            'mousemove.hc touchdrag.hc': function (e) {
                // Run beta
                newBeta = beta + (posX - e.pageX) / sensitivity;
                chart.options.chart.options3d.beta = newBeta;

                // Run alpha
                newAlpha = alpha + (e.pageY - posY) / sensitivity;
                chart.options.chart.options3d.alpha = newAlpha;

                chart.redraw(false);
            },
            'mouseup touchend': function () {
                $(document).unbind('.hc');
            }
        });
    });

});
</script>

<body>
	<jsp:include page="fragments/header.jsp" />
	<div class="containerHeader">
	
	</div>

	<div id="container" style="height: 400px"></div>

	<div class="container">
		<h3> <fmt:message  key="reportCenter.index.operationindex" /></h3>
		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>
		<div class="panel-group">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" href="#collapse1"><fmt:message  key="reportCenter.header.elements" /></a>
					</h4>
				</div>
				<div id="collapse1" class="panel-collapse collapse">
					<div class="panel-body">
					 <a href="${pageContext.request.contextPath}/elements.htm"><fmt:message  key="reportCenter.header.list" /></a>
					</div>
					<div class ="panel-footer">
						<a href="${pageContext.request.contextPath}/elements/add.htm">
						<fmt:message  key="reportCenter.new" /></a>
					</div>
				</div>
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" href="#collapse2">xPathBase</a>
					</h4>
				</div>
				<div id="collapse2" class="panel-collapse collapse">
					<div class="panel-body">
						<a href="${pageContext.request.contextPath}/xpathbase.htm">
							<fmt:message  key="reportCenter.header.list" /></a>
					</div>
					<div class="panel-footer">
						<a href="${pageContext.request.contextPath}/xpathbase/add.htm">
							<fmt:message  key="reportCenter.new" /></a>
					</div>
				</div>
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" href="#collapse6">Field</a>
					</h4>
				</div>
				<div id="collapse6" class="panel-collapse collapse">
					<div class="panel-body">
						<a href="${pageContext.request.contextPath}/field.htm"> <fmt:message  key="reportCenter.header.list" /></a>
					</div>
					<div class="panel-footer">
						<a href="${pageContext.request.contextPath}/field/add.htm">
							<fmt:message  key="reportCenter.new" /></a>
					</div>
				</div>
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" href="#collapse3">Template</a>
					</h4>
				</div>
				<div id="collapse3" class="panel-collapse collapse">
					<div class="panel-body">
						<a href="${pageContext.request.contextPath}/template.htm">
							<fmt:message  key="reportCenter.header.list" /> </a>
					</div>
					<div class="panel-footer">
						<a href="${pageContext.request.contextPath}/template/add.htm">
							<fmt:message  key="reportCenter.new" /> </a>
					</div>
					<div class="panel-body">
						<a
							href="${pageContext.request.contextPath}/template/customizeTemplate.htm">
							<fmt:message  key="reportCenter.header.templates.customizetemplates" /></a>
					</div>
					<div class="panel-footer">
						<a
							href="${pageContext.request.contextPath}/template/templateTicketGeneration.htm">
							<fmt:message  key="reportCenter.header.templates.templateTicketGeneration" /></a>
					</div>
					<div class="panel-footer">
						<a
							href="${pageContext.request.contextPath}/template/templateValidation.htm">
							<fmt:message  key="reportCenter.header.templates.templateValidation" /></a>
					</div>
				</div>
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" href="#collapse4"><fmt:message  key="reportCenter.header.ticket" /></a>
					</h4>
				</div>
				<div id="collapse4" class="panel-collapse collapse">
					<div class="panel-body">
						<a href="${pageContext.request.contextPath}/selectTicket.htm">
						<fmt:message  key="reportCenter.header.search" /></a>
					</div>
				</div>
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" href="#collapse5"><fmt:message  key="reportCenter.header.cmmmessage" /></a>
					</h4>
				</div>
				<div id="collapse5" class="panel-collapse collapse">
					<div class="panel-body">
						<a href="${pageContext.request.contextPath}/messagecmm/list.htm"> <fmt:message  key="reportCenter.header.list" /></a>
					</div>
					<div class="panel-footer">
						<a href="${pageContext.request.contextPath}/messagecmm/add.htm"> <fmt:message  key="reportCenter.new" /></a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="fragments/footer.jsp" />
</body>
</html>
