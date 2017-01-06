$(function () {
	// Age categories
	var categories = ['Shopper', 'Interaction', 'Interested', 'Product Zone', 'Shelve Transit', 'Total Customer'];
	$(document).ready(function () {
		$('#funChannelDiv').highcharts({
			chart: {
				type: 'bar'
			},
			title: {
				text: 'Orco'
			},
			subtitle: {
				text: 'Criterya'
			},
			xAxis: [{
				categories: categories,
				reversed: false,
				labels: {
					step: 1
				}
			}, { // mirror axis on right side
				opposite: true,
				reversed: false,
				categories: categories,
				linkedTo: 0,
				labels: {
					step: 1
				}
			}],
			yAxis: {
				title: {
					text: null
				},
				labels: {
					formatter: function () {
						return Math.abs(this.value);
					}
				}
			},
			  credits: {
			  enabled: false
			},
			plotOptions: {
				series: {
					stacking: 'normal'
				},
				bar: {
				  dataLabels: {
					  enabled: true,
					  formatter: function () {
					return Highcharts.numberFormat(Math.abs(this.point.y), 0);
				}
				  }
			  }
			},
			tooltip: {
				formatter: function () {
					return '<b>' + this.series.name + ', ' + this.point.category + '</b><br/>' +
						'Population: ' + Highcharts.numberFormat(Math.abs(this.point.y), 0);
				}
			},

			series: series
		});
	});
});