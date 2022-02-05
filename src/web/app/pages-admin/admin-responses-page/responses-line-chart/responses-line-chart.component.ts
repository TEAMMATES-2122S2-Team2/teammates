import { Component, ElementRef, Input, OnChanges, SimpleChanges } from '@angular/core';
import * as d3 from 'd3';

/**
 * Line chart for the feedback responses.
 *
 * Adapted from: https://medium.com/weekly-webtips/build-a-simple-line-chart-with-d3-js-in-angular-ccd06e328bff
 */
@Component({
  selector: 'tm-responses-line-chart',
  templateUrl: './responses-line-chart.component.html',
  styleUrls: ['./responses-line-chart.component.scss'],
})
export class ResponsesLineChartComponent implements OnChanges {

  @Input()
  data!: { value: number, date: string }[];

  @Input()
  timeRange: { startTime: number, endTime: number } = { startTime: 0, endTime: 0 };

  private width: number = 700;
  private height: number = 700;
  private margin: number = 50;
  private svg: any;
  private svgInner: any;
  private yScale: any;
  private xScale: any;
  private xAxis: any;
  private yAxis: any;
  private lineGroup: any;

  constructor(public chartElem: ElementRef) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.hasOwnProperty('data') && this.data && this.timeRange) {
      this.initializeChart();
      this.drawChart();

      window.addEventListener('resize', () => this.drawChart());
    }
  }

  private initializeChart(): void {
    if (this.svg) {
      d3.select('svg').remove();
    }
    const startTime: Date = new Date(this.timeRange.startTime);
    const endTime: Date = new Date(this.timeRange.endTime);
    this.svg = d3
      .select(this.chartElem.nativeElement)
      .select('.line-chart')
      .append('svg')
      .attr('height', this.height);

    this.svgInner = this.svg
      .append('g')
      .style('transform', `translate(${this.margin}px, ${this.margin}px)`);

    this.yScale = d3
      .scaleLinear()
      .domain([d3.max(this.data, (d: {value: number, date: string}) => d.value) + 1,
        d3.min(this.data, (d: {value: number, date: string}) => d.value) - 1])
      .range([0, this.height - 2 * this.margin]);

    this.xScale = d3
      .scaleTime()
      .domain([startTime, endTime]);

    this.yAxis = this.svgInner
      .append('g')
      .attr('id', 'y-axis')
      .style('transform', `translate(${this.margin}px, 0)`);

    this.xAxis = this.svgInner
      .append('g')
      .attr('id', 'x-axis')
      .style('transform', `translate(0, ${this.height - 2 * this.margin}px)`);

    this.lineGroup = this.svgInner
      .append('g')
      .append('path')
      .attr('id', 'line')
      .style('fill', 'none')
      .style('stroke', 'red')
      .style('stroke-width', '2px');
  }

  private drawChart(): void {
    this.width = this.chartElem.nativeElement.getBoundingClientRect().width;
    this.svg.attr('width', this.width);

    this.xScale.range([this.margin, this.width - 2 * this.margin]);

    const xAxis: any = d3
      .axisBottom(this.xScale)
      .ticks(10)
      .tickFormat(d3.timeFormat('%b %d, %H:%M'));

    this.xAxis.call(xAxis);

    const yAxis: any = d3
      .axisLeft(this.yScale);

    this.yAxis.call(yAxis);

    const line: any = d3
      .line()
      .x((d: number[]) => d[0])
      .y((d: number[]) => d[1])
      .curve(d3.curveMonotoneX);

    const points: [number, number][] = this.data.map((d: {value: number, date: string}) => [
      this.xScale(new Date(d.date)),
      this.yScale(d.value),
    ]);

    this.lineGroup.attr('d', line(points));

    var div = d3.select("div.tooltip");
    var obj = this;
    this.svgInner
      .selectAll("dot")
      .data(this.data)
      .enter()
      .append("circle")								
      .attr("r", 4)		
      .attr("cx", function(d:any) { return obj.xScale(new Date(d.date)); })		 
      .attr("cy", function(d:any) { return obj.yScale(d.value); })
      .on("mouseover", (d: any) => {
        div.transition()
          .duration(200)
          .style("opacity", .9);
        div.html("Time: " + new Date(d.date).toString() + "<br/>New Responses Count: " + d.value)
          .style("left", (d3.event.pageX) + "px")
          .style("top", (d3.event.pageY - 32) + "px");
        })
      .on("mouseout", () => {		
        div.transition()		
          .duration(500)		
          .style("opacity", 0);	
      });
  }

}
