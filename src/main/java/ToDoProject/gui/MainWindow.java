package ToDoProject.gui;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.Box.createVerticalStrut;
import static java.lang.Math.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import ToDoProject.core.TodoList;

public class MainWindow extends JFrame {

	private JPanel mainContentPane;
	private JPanel newTaskControls;
	private JButton addTaskButton;
	private JList<String> chooseTaskButton;
	private JTextField newTaskField;
	private JComboBox<String> newCatField;
	private JScrollPane taskListScrollPane;
	private JPanel taskListControls;
	private JButton upButton;
	private JButton deleteButton;
	private JButton downButton;
	private JList<String> taskList;
	private JLabel statusBar;

	private JButton saveButton;
	private JButton saveAsButton;

	private JCheckBox checkBox;

	private TodoList todoList;
	private TodoListModel todoListModel;
	private DefaultListModel model;

	public MainWindow() {

		this.todoList = new TodoList();
		this.todoListModel = new TodoListModel(this.todoList);

		this.setContentPane(this.getMainContentPane());

		this.setTitle("Список дел.");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setMinimumSize(new Dimension(500, 500));

		this.pack();
	}

	private Container getMainContentPane() {
		if (mainContentPane == null) {
			this.mainContentPane = new JPanel();
			this.mainContentPane.setLayout(new BorderLayout());

			this.mainContentPane.add(getNewTaskControls(), BorderLayout.NORTH);
			this.mainContentPane.add(getTasksListScrollPane(), BorderLayout.CENTER);
			this.mainContentPane.add(getTasksListControls(), BorderLayout.EAST);
			this.mainContentPane.add(getStatusBar(), BorderLayout.SOUTH);

		}
		return this.mainContentPane;
	}

	private Component getNewTaskControls() {

		if (this.newTaskControls == null) {
			this.newTaskControls = new JPanel();

			// Use BoxLayout for vertical arrangement
			this.newTaskControls.setLayout(new BoxLayout(this.newTaskControls, BoxLayout.Y_AXIS));
			this.newTaskControls.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

			JTextField categoryField = new JTextField("Enter category here");
			categoryField.setBackground(Color.gray); // Set background color to red
			this.newTaskControls.add(categoryField);

			String[] categories = { "Category 1", "Category 2", "Category 3" };
			JComboBox<String> categoryField2 = new JComboBox<>(categories);
			this.newTaskControls.add(categoryField2);

			this.newTaskField = categoryField;
			this.newCatField = categoryField2;
			// Add add task button
			// JButton addTaskButton = new JButton("Add Task");
			// this.newTaskControls.add(getAddTaskButton());
			this.newTaskControls.add(getAddTaskButton());
			this.newTaskControls.add(chooseTaskButton());
		}
		return this.newTaskControls;
	}

	private JComboBox<String> getCatField() {
		return this.newCatField;
	}

	private JTextField getNewTaskField() {
		if (this.newTaskField == null) {
			this.newTaskField = new JTextField();
		} else {
			System.out.println("empty");
		}
		return this.newTaskField;
	}

	private Component getTasksListScrollPane() {
		if (this.taskListScrollPane == null) {
			this.taskListScrollPane = new JScrollPane(getTaskList());

		}

		return this.taskListScrollPane;
	}

	private JList<String> getTaskList() {
		if (this.taskList == null) {
			this.taskList = new JList<>();
			this.taskList.add(getCheckBox());
			this.taskList.setModel(this.todoListModel);
		}

		return this.taskList;
	}

	private Component getTasksListControls() {
		if (this.taskListControls == null) {
			this.taskListControls = new JPanel();

			BoxLayout layout = new BoxLayout(this.taskListControls, BoxLayout.Y_AXIS);
			this.taskListControls.setLayout(layout);
			this.taskListControls.setBorder(createEmptyBorder(5, 5, 5, 5));

			JButton button = getUpButton();
			button.setAlignmentX(CENTER_ALIGNMENT);
			this.taskListControls.add(button);

			this.taskListControls.add(createVerticalStrut(10));

			button = getDeleteButton();
			button.setAlignmentX(CENTER_ALIGNMENT);
			this.taskListControls.add(button);

			this.taskListControls.add(createVerticalStrut(10));

			button = getDownButton();
			button.setAlignmentX(CENTER_ALIGNMENT);
			this.taskListControls.add(button);

			this.taskListControls.add(createVerticalStrut(10));

			button = getSaveButton();
			button.setAlignmentX(CENTER_ALIGNMENT);
			this.taskListControls.add(button);

			this.taskListControls.add(createVerticalStrut(10));

			button = getSaveAsButton();
			button.setAlignmentX(CENTER_ALIGNMENT);
			this.taskListControls.add(button);

			this.taskListControls.add(createVerticalStrut(10));
		}

		return this.taskListControls;
	}

	private JButton getUpButton() {
		if (this.upButton == null) {
			this.upButton = new JButton("Вверх");
			this.upButton.setIcon(createIcon("up.png"));

			this.upButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int pos = getTaskList().getSelectedIndex();
					todoListModel.moveUp(pos);

					getTaskList().setSelectedIndex(max(0, pos - 1));
				}
			});
		}

		return this.upButton;
	}

	private JButton getDeleteButton() {
		if (this.deleteButton == null) {
			this.deleteButton = new JButton("Удалить");
			this.deleteButton.setIcon(createIcon("bin.png"));

			this.deleteButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					todoListModel.removeAt(getTaskList().getSelectedIndex());
				}
			});
		}

		return this.deleteButton;
	}

	private JButton getDownButton() {
		if (this.downButton == null) {
			this.downButton = new JButton("Вниз");
			this.downButton.setIcon(createIcon("down.png"));

			this.downButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int pos = getTaskList().getSelectedIndex();
					todoListModel.moveDown(pos);

					getTaskList().setSelectedIndex(
							min(getTaskList().getModel().getSize() - 1, pos + 1));
				}
			});
		}

		return this.downButton;
	}

	private JList<String> chooseTaskButton() {
		if (this.chooseTaskButton == null) {
			// .addTaskButton = new JButton("Добавить");
			this.chooseTaskButton = new JList<>(new String[] { "Category 1", "Category 2", "Category 3" });
			// this.addTaskButton.setIcon(createIcon("diary.png"));
			// this.chooseTaskButton.setIcon(createIcon("diary.png"));
			this.chooseTaskButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					String selectedItem = chooseTaskButton.getSelectedValue();

					// Pass the selected item to filterTaskList
					filterTaskList(selectedItem);
				}
			});

		}

		return this.chooseTaskButton;

	}

	private JButton getAddTaskButton() {
		if (this.addTaskButton == null) {
			this.addTaskButton = new JButton("Добавить");
			// this.chooseTaskButton = new JButton("Добавить2");
			this.addTaskButton.setIcon(createIcon("diary.png"));
			// this.chooseTaskButton.setIcon(createIcon("diary.png"));
			this.addTaskButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					System.out.println("hello");
					System.out.println(getCatField().getSelectedItem().toString());
					// print('a');
					if (getNewTaskField().getText().length() > 0) {
						todoListModel
								.add(getNewTaskField().getText().trim() + ":" + getCatField().getSelectedItem());

						getNewTaskField().setText("");
						// getCatField().setText("");

						getTaskList().setSelectedIndex(getTaskList().getModel().getSize() - 1);
					}
				}
			});
		}

		return this.addTaskButton;
	}

	private JLabel getStatusBar() {
		if (this.statusBar == null) {
			this.statusBar = new JLabel("Количество дел: 0");
			this.todoListModel.addListDataListener(new ListDataListener() {
				@Override
				public void contentsChanged(ListDataEvent e) {
					updateLabel(e);
				}

				private void updateLabel(ListDataEvent e) {
					getStatusBar().setText("Количество дел: " + ((TodoListModel) e.getSource()).getSize());
				}

				@Override
				public void intervalRemoved(ListDataEvent e) {
				}

				@Override
				public void intervalAdded(ListDataEvent e) {
				}
			});
		}

		return this.statusBar;
	}

	private Icon createIcon(String iconfilename) {
		return new ImageIcon(
				getClass().getResource("/" + iconfilename));
	}

	private JCheckBox getCheckBox() {
		if (this.checkBox == null) {
			this.checkBox = new JCheckBox();
		}

		return this.checkBox;
	}

	private void filterTaskList(String filterBy) {
		final List<String> filteredItems = new ArrayList<>();
		for (int i = 0; i < getTaskList().getModel().getSize(); i++) {
			String item = getTaskList().getModel().getElementAt(i);
			// Example condition: filter items that contain the word "important"
			if (item.contains(filterBy)) {
				filteredItems.add(item);
			}
		}

		// Update the JList model with the filtered items
		getTaskList().setModel(new AbstractListModel<String>() {
			@Override
			public int getSize() {
				return filteredItems.size();
			}

			@Override
			public String getElementAt(int index) {
				return filteredItems.get(index);
			}
		});
	}

	private JButton getSaveButton() {
		if (this.saveButton == null) {
			this.saveButton = new JButton("Сохранить");
			this.saveButton.setIcon(createIcon("save.png"));

			this.saveButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int value = todoList.size();
					PrintWriter printWriter = null;
					try {
						printWriter = new PrintWriter("OurSavedFile.txt");
						printWriter.println(value);
						for (int i = 0; i < value; i++) {
							printWriter.println(todoList.elementAt(i));
						}
						printWriter.close();
					} catch (Exception ex) {
						System.out.println("" + ex);
					}
				}
			});
		}
		return this.saveButton;
	}

	private JButton getSaveAsButton() {
		if (this.saveAsButton == null) {
			this.saveAsButton = new JButton("Сохранить как");
			this.saveAsButton.setIcon(createIcon("save_as.png"));

			this.saveAsButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					int ret = fileChooser.showDialog(null, "Сохранить как");
					if (ret == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						int value = todoList.size();
						PrintWriter printWriter = null;
						try {
							printWriter = new PrintWriter(file);
							printWriter.println(value);
							for (int i = 0; i < value; i++) {
								printWriter.println(todoList.elementAt(i));
							}
							printWriter.close();
						} catch (Exception ex) {
							System.out.println("" + ex);
						}
					}
				}
			});
		}
		return this.saveAsButton;
	}

}
