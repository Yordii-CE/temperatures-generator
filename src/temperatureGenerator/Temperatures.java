package temperatureGenerator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Temperatures extends JFrame {
	Object[][] datos = null;
    private JTable table;
    private JButton btnMostrarTabla;
    private JButton btnMostrarProyeccion;

    public Temperatures() {
        setTitle("Proyeccion de temperaturas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        btnMostrarTabla = new JButton("Generar temperaturas");
        btnMostrarTabla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	datos = generarDatos();
            	btnMostrarProyeccion.setEnabled(true);
            	btnMostrarTabla.setText("Generar otras temperaturas");

            }
        });

        btnMostrarProyeccion = new JButton("Ver Proyeccion");
        btnMostrarProyeccion.setEnabled(false);
        btnMostrarProyeccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarTabla();
            	btnMostrarProyeccion.setEnabled(false);
            }
        });

        JPanel panel = new JPanel();
        panel.add(btnMostrarTabla);
        panel.add(btnMostrarProyeccion);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.NORTH);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void mostrarTabla() {
    	if(datos == null) {
    		 JOptionPane.showMessageDialog(null, "Generar primero las temperaturas", "Alerta", JOptionPane.WARNING_MESSAGE);
    		 return;
    	}
        String[] columnas = {"DIA", "T.Min", "T.Max"};

        DefaultTableModel model = new DefaultTableModel(datos, columnas);
        model.addColumn("T.Promedio");
        calcularProyeccion(model);

        table.setModel(model);
    }

    private Object[][] generarDatos() {
        Random random = new Random();
        Object[][] datos = new Object[15][3];

        for (int i = 0; i < 15; i++) {
            int dia = i + 1;
            int tMin = random.nextInt(35) + 6;
            int tMax = random.nextInt(35) + 6;
            datos[i] = new Object[]{dia + "", tMin + "", tMax + ""};
        }

        return datos;
    }

    private void calcularProyeccion(DefaultTableModel model) {
        for (int row = 0; row < model.getRowCount(); row++) {
            int tMin = Integer.parseInt(model.getValueAt(row, 1).toString());
            int tMax = Integer.parseInt(model.getValueAt(row, 2).toString());
            int promedio = (tMin + tMax) / 2;
            model.setValueAt(promedio + "", row, 3);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	Temperatures frame = new Temperatures();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}



