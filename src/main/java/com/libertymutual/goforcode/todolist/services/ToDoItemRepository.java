package com.libertymutual.goforcode.todolist.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.todolist.models.ToDoItem;

@Service
public class ToDoItemRepository {

	private int nextId = 1;
	private ArrayList<ToDoItem> itemList;

	public ToDoItemRepository() {

	}

	/**
	 * Get all the items from the file.
	 * 
	 * @return A list of the items. If no items exist, returns an empty list.
	 */
	public List<ToDoItem> getAll() {

		try (FileReader reader = new FileReader("todolist.csv");
				CSVParser parser = new CSVParser(reader, CSVFormat.RFC4180);) {

			List<CSVRecord> record = CSVFormat.DEFAULT.parse(reader).getRecords();
			itemList = new ArrayList<ToDoItem>();
			int existingID = 0;

			for (CSVRecord current : record) {
				ToDoItem item = new ToDoItem();
				item.setId(Integer.parseInt(current.get(0)));
				item.setText(current.get(1));
				item.setComplete(Boolean.parseBoolean(current.get(2)));
				itemList.add(item);
				if (Integer.parseInt(current.get(0)) > existingID) {
					existingID = Integer.parseInt(current.get(0));
				}

				nextId = existingID + 1;
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println("Could not find file.");
		} catch (IOException ioe) {
			System.err.println("Could not read file.");
		}

		if (itemList.size() == 0) {
			return Collections.emptyList();
		}

		return itemList;

	}

	/**
	 * Assigns a new id to the ToDoItem and saves it to the file.
	 * 
	 * @param item
	 *            The to-do item to save to the file.
	 */
	public void create(ToDoItem item) {
		item.setId(nextId);
		nextId += 1;
		try (FileWriter writer = new FileWriter("todolist.csv", true);
				CSVPrinter printer = new CSVPrinter(writer, CSVFormat.RFC4180)) {
			String[] record = { Integer.toString((item.getId())), item.getText(), Boolean.toString(item.isComplete()) };
			printer.printRecord(record);
		} catch (FileNotFoundException fnfe) {
			System.err.println("Could not find file.");
		} catch (IOException ioe) {
			System.err.println("Could not read file.");
		}
	}

	/**
	 * Gets a specific ToDoItem by its id.
	 * 
	 * @param id
	 *            The id of the ToDoItem.
	 * @return The ToDoItem with the specified id or null if none is found.
	 */
	public ToDoItem getById(int id) {

		for (ToDoItem current : itemList) {
			if (current.getId() == id) {
				return current;
			}
		}
		return null;
	}

	/**
	 * Updates the given to-do item in the file.
	 * 
	 * @param item
	 *            The item to update.
	 */
	public void update(ToDoItem item) {
		for (ToDoItem current : itemList) {
			if (current.getId() == item.getId()) {
				item.setComplete(true);
			}
		}
		try (FileWriter writer = new FileWriter("todolist.csv");
				CSVPrinter printer = new CSVPrinter(writer, CSVFormat.RFC4180)) {

			for (ToDoItem current1 : itemList) {
				String[] record = { Integer.toString((current1.getId())), current1.getText(),
						Boolean.toString(current1.isComplete()) };
				printer.printRecord(record);
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println("Could not find file.");
		} catch (IOException ioe) {
			System.err.println("Could not read file.");
		}

	}
}
