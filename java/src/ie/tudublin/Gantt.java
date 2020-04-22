package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class Gantt extends PApplet
{	
	ArrayList<Task> tasks = new ArrayList<Task>();
	Task chosen;

	public void settings()
	{
		size(800, 600);
	}

	public void loadTasks()
	{
		Table t = loadTable("tasks.csv", "header");
		for(TableRow tr:t.rows())
		{
			Task ta = new Task(tr);
			tasks.add(ta);
		}
	}

	public void printTasks()
	{
		for(Task t:tasks)
		{
			println(t);
		}
	}
	
	public void mousePressed()
	{
		float border = width * 0.05f;
		println("Mouse pressed");
		
		for(int i = 0; i < tasks.size(); i++)
		{
			Task t = tasks.get(i);
			float gap = map(i, 0, tasks.size(), border + 30, height - border);
			float tStart = map(t.getStart(), 1, 30, border + 100, width - border);
			float tEnd = map(t.getEnd(), 1, 30, border + 100, width - border);
			if(mouseX <= tStart + 20 &&
			mouseX >= tStart &&
			mouseY >= gap -15 &&
			mouseY <= gap + 15)
			{
				println("You chose the start of " + t);
				chosen = t;
			}

			if(mouseX >= tEnd - 20 &&
			mouseX <= tEnd &&
			mouseY >= gap -15 &&
			mouseY <= gap + 15)
			{
				println("You chose the end of " + t);
				chosen = t;
			}
		}
	}

	public void mouseDragged()
	{
		float border = width * 0.05f;
		println("Mouse dragged");

		if(chosen != null)
		{
			float tStart = map(chosen.getStart(), 1, 30, border + 100, width - border);
			float tEnd = map(chosen.getEnd(), 1, 30, border + 100, width - border);
			int diff = chosen.getEnd() - chosen.getStart();

			if(mouseX >= tEnd + 20 && chosen.getEnd() < 30)
			{
				chosen.setEnd(chosen.getEnd() + 1);
			}
			else if(mouseX <= tEnd && mouseX > tEnd - 20 && diff > 1)
			{
				chosen.setEnd(chosen.getEnd() - 1);
			}
			else if(mouseX >= tStart && mouseX < tStart + 20 && diff > 1)
			{
				chosen.setStart(chosen.getStart() + 1);
			}
			else if(mouseX <= tStart - 20 && chosen.getStart() > 1)
			{
				chosen.setStart(chosen.getStart() - 1);
			}
		}
	}

	public void displayTasks()
	{
		float border = width * 0.05f;
		float gap;
		int maxLines = 30;

		colorMode(HSB);
		textAlign(CENTER, CENTER);
		stroke(255);
		fill(255);
		
		for(int i = 1; i <= maxLines; i++)
		{
			gap = map(i, 1, maxLines, border + 100, width - border);
			text(i, gap, border - 20);
			line(gap, border, gap, height - border);
		}

		textAlign(LEFT, CENTER);
		float rectH = 30;
		float roundEdge = 5;

		for(int i = 0; i < tasks.size(); i++)
		{
			gap = map(i, 0, tasks.size(), border + 30, height - border);

			stroke(255);
			fill(255);
			Task t = tasks.get(i);
			text(t.getTask(), border, gap);

			fill(map(i, 0, tasks.size(), 0, 255), 255, 255);
			noStroke();

			float tStart = map(t.getStart(), 1, 30, border + 100, width - border);
			float tEnd = map(t.getEnd(), 1, 30, border + 100, width - border);
			rect(
				tStart,
				gap - 15,
				abs(tStart - tEnd),
				rectH,
				roundEdge
			);
		}
	}
	
	
	public void setup() 
	{
		loadTasks();
		printTasks();
	}
	
	public void draw()
	{			
		background(0);
		displayTasks();
	}
}
