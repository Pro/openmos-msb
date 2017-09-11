/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.openmos.msb.starter;


import eu.openmos.agentcloud.cloudinterface.AgentStatus;
import eu.openmos.msb.database.stateless.DeviceRegistryBean;
import eu.openmos.msb.opcua.utils.OPCDeviceDiscoveryItf;
import eu.openmos.agentcloud.config.ConfigurationLoader;
import eu.openmos.agentcloud.data.CyberPhysicalAgentDescription;
import eu.openmos.msb.dummyclasses.ExecuteData;
import eu.openmos.msb.dummyclasses.ServerStatus;
import eu.openmos.msb.opcua.milo.server.opcuaServerMSB;
import eu.openmos.msb.opcua.utils.OPCDeviceItf;
import eu.openmos.msb.opcua.utils.OpcUaServersDiscoverySnippet;
import eu.openmos.msb.recipesmanagement.RecipesDeployerImpl;
import static eu.openmos.msb.starter.MyHashMaps.ExecutiontTableMaps;
import static eu.openmos.msb.starter.MyHashMaps.ProductIDAdapterMaps;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultCaret;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.Endpoint;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author fabio.miranda
 */
public class MSB_gui extends javax.swing.JFrame {

   
    private static final Map<String, Object> OPCclientIDMap = new HashMap<String, Object>();
    private static String MSB_OPCUA_SERVER_ADDRESS = null;

    private static eu.openmos.msb.opcua.milo.server.opcuaServerMSB opcuaServerInstanceMILO;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    
    private static DefaultTableModel TServersmodel;
    private static DefaultTableModel TProductsmodel;
    private static DefaultTableModel TDevicesmodel;

    private static ImageIcon redLight;
    private static ImageIcon greenLight;
    private static ImageIcon greyLight;
    
    private static ImageIcon iconPROD_A;
    private static ImageIcon iconPROD_B;
    
    private static JLabel labelLDS;
    private static JLabel labelServer;
    private static JLabel labelWS;
    private static JLabel labelRegister;
    
    private static OPCDeviceItf DeviceITF;
    
    public MSB_gui() throws Exception {
        initComponents();
        
        TServersmodel=(DefaultTableModel)TableServers.getModel();
        TProductsmodel=(DefaultTableModel)ProductsTable.getModel();
        TDevicesmodel=(DefaultTableModel)DevicesTable.getModel();
        prodA.hide();
        prodB.hide();
        
        ProductsTable.getModel().addTableModelListener(new CheckBoxModelListener());
        
        OnOffServerPanel.setLayout(new FlowLayout());
        OnOffRegister.setLayout(new FlowLayout());
        OnOffWSPanel.setLayout(new FlowLayout());
        OnOffLDS.setLayout(new FlowLayout());
      
        //faz o setup das imagens e dimensiona para o tamanho pretendido
        setImage();
        labelServer = new JLabel(redLight);
        OnOffServerPanel.add(labelServer);
        OnOffServerPanel.setMaximumSize(new Dimension(34, 31));
        
        labelLDS = new JLabel(redLight);
        OnOffLDS.add(labelLDS);
        OnOffLDS.setMaximumSize(new Dimension(34, 31));
        
        labelRegister = new JLabel(redLight);
        OnOffRegister.add(labelRegister);
        OnOffRegister.setMaximumSize(new Dimension(34, 31));
        
        labelWS = new JLabel(redLight);
        OnOffWSPanel.add(labelWS);
        OnOffWSPanel.setMaximumSize(new Dimension(34, 31));
        
        DeviceITF = new OPCDeviceItf(); //inputs? endpoints, MAP<ID, OPCclientObject> ?
        
        /*TableModelListener TML = null;
        ProductsTable.getModel().addTableModelListener(TML);
        TableColumn statusColumn = ProductsTable.getColumnModel().getColumn(1); //Status column ->activated/deactivated
        JCheckBox recipeStatus = null;
        statusColumn.setCellEditor(new DefaultCellEditor(recipeStatus));*/
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        opc_comms_log = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        msb_opcua_servername = new javax.swing.JTextField();
        StartMSBServer = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        LDSserverAddress = new javax.swing.JTextField();
        LDSRegisterserver = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btn_start_discovery = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        OnOffRegister = new javax.swing.JPanel();
        OnOffServerPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        StartWebService = new javax.swing.JButton();
        OnOffWSPanel = new javax.swing.JPanel();
        OnOffLDS = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        WebServiceAddress = new javax.swing.JTextField();
        openmoslogo = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableServers = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        ProductsTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        choice1 = new java.awt.Choice();
        choice2 = new java.awt.Choice();
        choice3 = new java.awt.Choice();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btn_sendRecipe = new javax.swing.JButton();
        comboServers = new javax.swing.JComboBox<>();
        btn_RequestProduct = new javax.swing.JButton();
        btn_DeviceRegistration = new javax.swing.JButton();
        btn_SendURL = new javax.swing.JButton();
        ComboMSB = new javax.swing.JComboBox<>();
        btn_ChangedState = new javax.swing.JButton();
        textToSend = new javax.swing.JTextField();
        btn_RecipeExecutionDone = new javax.swing.JButton();
        btn_sendrecipe2 = new javax.swing.JButton();
        btn_updatestatus = new javax.swing.JButton();
        btn_invoqueMethod = new javax.swing.JButton();
        prodA = new javax.swing.JButton();
        prodB = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        DevicesTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        opc_comms_log.setEditable(false);
        opc_comms_log.setColumns(20);
        opc_comms_log.setRows(5);
        opc_comms_log.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(opc_comms_log);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("MSB OPCUA Server");

        msb_opcua_servername.setText("opc.tcp://192.1.0.55:12637/MSB-OPCUA-SERVER");
        msb_opcua_servername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msb_opcua_servernameActionPerformed(evt);
            }
        });

        StartMSBServer.setText("Start");
        StartMSBServer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StartMSBServerMouseClicked(evt);
            }
        });
        StartMSBServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartMSBServerActionPerformed(evt);
            }
        });

        jLabel7.setText("LDS Server");

        LDSserverAddress.setText("opc.tcp://localhost:4840");
        LDSserverAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LDSserverAddressActionPerformed(evt);
            }
        });

        LDSRegisterserver.setText("Register");
        LDSRegisterserver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LDSRegisterserverActionPerformed(evt);
            }
        });

        jLabel6.setText("Discovery Service");

        btn_start_discovery.setText("Start");
        btn_start_discovery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_start_discoveryActionPerformed(evt);
            }
        });

        OnOffRegister.setPreferredSize(new java.awt.Dimension(34, 31));

        javax.swing.GroupLayout OnOffRegisterLayout = new javax.swing.GroupLayout(OnOffRegister);
        OnOffRegister.setLayout(OnOffRegisterLayout);
        OnOffRegisterLayout.setHorizontalGroup(
            OnOffRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );
        OnOffRegisterLayout.setVerticalGroup(
            OnOffRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        OnOffServerPanel.setPreferredSize(new java.awt.Dimension(34, 31));

        javax.swing.GroupLayout OnOffServerPanelLayout = new javax.swing.GroupLayout(OnOffServerPanel);
        OnOffServerPanel.setLayout(OnOffServerPanelLayout);
        OnOffServerPanelLayout.setHorizontalGroup(
            OnOffServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );
        OnOffServerPanelLayout.setVerticalGroup(
            OnOffServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jLabel2.setText("WebService");

        StartWebService.setText("Start");
        StartWebService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartWebServiceActionPerformed(evt);
            }
        });

        OnOffWSPanel.setPreferredSize(new java.awt.Dimension(34, 31));

        javax.swing.GroupLayout OnOffWSPanelLayout = new javax.swing.GroupLayout(OnOffWSPanel);
        OnOffWSPanel.setLayout(OnOffWSPanelLayout);
        OnOffWSPanelLayout.setHorizontalGroup(
            OnOffWSPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );
        OnOffWSPanelLayout.setVerticalGroup(
            OnOffWSPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        OnOffLDS.setPreferredSize(new java.awt.Dimension(34, 31));

        javax.swing.GroupLayout OnOffLDSLayout = new javax.swing.GroupLayout(OnOffLDS);
        OnOffLDS.setLayout(OnOffLDSLayout);
        OnOffLDSLayout.setHorizontalGroup(
            OnOffLDSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );
        OnOffLDSLayout.setVerticalGroup(
            OnOffLDSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        WebServiceAddress.setText("http://0.0.0.0:9997/wsRecipesDeployer");
        WebServiceAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WebServiceAddressActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel1)
                            .addComponent(msb_opcua_servername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(StartMSBServer)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_start_discovery)
                            .addComponent(LDSserverAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LDSRegisterserver)
                            .addComponent(jLabel2)
                            .addComponent(StartWebService)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(WebServiceAddress, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(OnOffRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(OnOffServerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(OnOffWSPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(OnOffLDS, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(openmoslogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(msb_opcua_servername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(OnOffServerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StartMSBServer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(LDSserverAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(OnOffRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addComponent(LDSRegisterserver)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_start_discovery))
                    .addComponent(OnOffLDS, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(WebServiceAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(OnOffWSPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)))
                .addComponent(StartWebService)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(openmoslogo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        TableServers.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableServers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Server Name", "Protocol", "URL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(TableServers);

        ProductsTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ProductsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product", "Recipe", "Status", "WorkStation"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(ProductsTable);

        jLabel3.setText("Products Table");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Adapters Table");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jList1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_sendRecipe.setText("Call SendRecipe");
        btn_sendRecipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendRecipeActionPerformed(evt);
            }
        });

        comboServers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboServersActionPerformed(evt);
            }
        });

        btn_RequestProduct.setText("Call RequestProduct");
        btn_RequestProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RequestProductActionPerformed(evt);
            }
        });

        btn_DeviceRegistration.setText("Call DeviceRegistration");
        btn_DeviceRegistration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeviceRegistrationActionPerformed(evt);
            }
        });

        btn_SendURL.setText("Call SendURL");
        btn_SendURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SendURLActionPerformed(evt);
            }
        });

        btn_ChangedState.setText("Call ChangedState");
        btn_ChangedState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChangedStateActionPerformed(evt);
            }
        });

        textToSend.setText("insert data to send");

        btn_RecipeExecutionDone.setText("Call RecipeExecutionDone");
        btn_RecipeExecutionDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RecipeExecutionDoneActionPerformed(evt);
            }
        });

        btn_sendrecipe2.setText("Call SendRecipe");
        btn_sendrecipe2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendrecipe2ActionPerformed(evt);
            }
        });

        btn_updatestatus.setText("Call StatusUpdate");
        btn_updatestatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updatestatusActionPerformed(evt);
            }
        });

        btn_invoqueMethod.setText("Call InvoqueMethod");
        btn_invoqueMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_invoqueMethodActionPerformed(evt);
            }
        });

        prodA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prodAActionPerformed(evt);
            }
        });

        prodB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prodBActionPerformed(evt);
            }
        });

        jButton1.setText("MasmecMagic");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(comboServers, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btn_RequestProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_SendURL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_sendRecipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_invoqueMethod, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(prodA, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(prodB, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textToSend, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(ComboMSB, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_updatestatus, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btn_ChangedState, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_RecipeExecutionDone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_sendrecipe2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(btn_DeviceRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(46, 46, 46))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboServers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboMSB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textToSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_DeviceRegistration)
                            .addComponent(btn_SendURL))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_RecipeExecutionDone)
                            .addComponent(btn_RequestProduct))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_ChangedState)
                            .addComponent(btn_sendRecipe))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_sendrecipe2)
                            .addComponent(btn_invoqueMethod))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_updatestatus)
                            .addComponent(jButton1)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(prodA, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(prodB, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        DevicesTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        DevicesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Status", "Endpoint", "WorkStation"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(DevicesTable);

        jLabel5.setText("Devices Table");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Test Bench");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator4)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(jScrollPane1))
                                        .addContainerGap())
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel5)
                                        .addGap(302, 302, 302))))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(296, 296, 296))))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(503, 503, 503)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(493, 493, 493)
                        .addComponent(jLabel4)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(2, 2, 2)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_start_discoveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_start_discoveryActionPerformed

        /*addToTableProduct("A", "PUDIM", true, "ME");
        addToTableProduct("B", "FLAN", true, "ME");
         DeviceRegistryBean dbMSBd = new DeviceRegistryBean();
        dbMSBd.register_execution_info("workstattt", "pudim", "NO AGENT", "prod_id", "methodID", "objectID"); //DELETE*/
               
//delete old msb servers in the DB at the startup - they could have the same address 
        DeviceRegistryBean dbMSB = new DeviceRegistryBean();
        ArrayList ArrayData = dbMSB.list_all_devices();
        for (int i = 0; i < ArrayData.size(); i++) {
            if (ArrayData.get(i).toString().contains("MSB")) {
                System.out.println("found old MSB instance on database: " + i);
                dbMSB.deregister_device(ArrayData.get(i).toString());
            }
        }
                
//launch Discovery Service to search for other devices (OPCUA servers from devices)
        OPCDeviceDiscoveryItf OPCdevDiscItf;
        OPCdevDiscItf = new OPCDeviceDiscoveryItf() {
            @Override
            public void on_new_server(String name, String app_uri) {
                System.out.println("server found: " + app_uri);

                labelLDS.setIcon(greenLight); //blink status LED every polling cycle
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
                }
                labelLDS.setIcon(greyLight);    
            }

            @Override
            public void on_new_endpoint(String name, String app_uri) {
                //every time there is a "polling" cycle in discovery, check OPC client/server connections
                System.out.println("POLLING CYCLE********************************");
                if (OPCclientIDMap.size() > 0) { //verify client connection status in the end of the polling cycle

                }
                System.out.println("NAME: " + name + " URL: " + app_uri);

                DeviceRegistryBean dbMSB = new DeviceRegistryBean();
                ArrayList ArrayData = dbMSB.read_device_info(name);

                if (ArrayData.isEmpty()) { //if the device name doesn't exist in the database, register
                    registerServer(name, app_uri);
                    addToTableServers(name, "opcua", app_uri);
                    System.out.println("onNewServer Registered Server: " + name + " " + app_uri);
                    opc_comms_log.append("New Server found and registered: " + name + " " + app_uri+"\n");
                    if (name.contains("MSB")) {
                        MSB_OPCUA_SERVER_ADDRESS = app_uri;
                        ComboMSB.addItem(name);
                    }

                    if (app_uri.contains("4840")) { //replace this with org.eclipse.milo.opcua.stack.core.types.structured.ApplicationDescription serverList = UaTcpStackClient.findServers(app_uri).get(); //new MSB
                        //s.getApplicationType()!=org.eclipse.milo.opcua.stack.core.types.enumerated.ApplicationType.DiscoveryServer
                        System.out.println("Don't create an opc client for LDS!");
                    } else {
                        try {
                            
                            MSB_MiloClientSubscription instance = new MSB_MiloClientSubscription();
                            
                             OPCclientIDMap.put(name, instance); //save the client objectID and the name of the device as hashmap
                            //MyHashMaps hash = null;

                            MyHashMaps myOpcUaClientsMap = MyHashMaps.getInstance(); //singleton to access client objects in other classes
                            myOpcUaClientsMap.setOPCclientIDMaps(name, instance);
                            
                            //start connection after inserting on the hashmap!
                            instance.startConnection(app_uri);

                          
                            // Iterate over all values, using the keySet method.
                            //call SendServerURL() method from device
                            OpcUaClient client = instance.getClientObject();
                            if (!name.contains("MSB")) {
                                comboServers.addItem(name);
                                instance.SendServerURL(client, MSB_OPCUA_SERVER_ADDRESS).exceptionally(ex -> {
                                    System.out.println("error invoking SendServerURL() for server: " + name + "\n" + ex); //logger.error("error invoking SendServerURL()", ex);
                                    return "-1.0";
                                }).thenAccept(v -> {
                                    //logger.info("SendServerURL(cenas)={}", v);
                                    System.out.println("SendServerURL(uri)={}\n" + v);
                                    //future.complete(client);
                                });
                                
                                
                            }

                            if (client == null) {
                                System.out.println("Client = null?");
                            }

                            for (String key : OPCclientIDMap.keySet()) {
                                System.out.println(key + " ->hashmap<- - " + OPCclientIDMap.get(key));
                            }
                            
                            for (String key : myOpcUaClientsMap.getOPCclientIDMaps().keySet()) {
                                System.out.println(key + " ->singleton hashmap<- - " + myOpcUaClientsMap.getOPCclientIDMaps().get(key));
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } else {  //if the device name exist in the database, check if it is still available
                    System.out.println("Server : " + name + " " + app_uri + " Already registered in the Database!");
                }
            }

            @Override
            public void on_endpoint_dissapeared() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void reset_tables() {
                // dropServersAndEndpoints(); //ver
                System.out.println("reset endpoint and servers tables...");
            }

            @Override
            public void on_server_dissapeared(String name, String app_uri) {
                System.out.println("This server has disapeared: " + name);
                if(RemoveDownServer(name)==1){
                    opc_comms_log.append("The server: " +name+" has disapeared and has been successfully removed from database.\n");
                    //deleteFromTableServers(name, "", app_uri);
                    CleanTablesFromWorkstation(name);
                    //wait for completion? warning
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    CleanHashMapsFromWorkstation(name);
                    
                }else{
                    opc_comms_log.append("The server: " +name+" has disapeared and has NOT been removed from database.\n");
                }
                for (String key : OPCclientIDMap.keySet()) {
                    System.out.println(key + " ->new local hashmap<- - " + OPCclientIDMap.get(key));
                }
                MyHashMaps myOpcUaClientsMap = MyHashMaps.getInstance(); //singleton to access client objects in other classes
                for (String key : myOpcUaClientsMap.getOPCclientIDMaps().keySet()) {
                    System.out.println(key + " ->new singleton hashmap<- - " + myOpcUaClientsMap.getOPCclientIDMaps().get(key));
                }
            }

        };
        // String LDS_uri="opc.tcp://localhost:4840";
        String LDS_uri = LDSserverAddress.getText();

        OpcUaServersDiscoverySnippet OPCDiscoverySnippet = new OpcUaServersDiscoverySnippet(LDS_uri, OPCdevDiscItf); 
        OPCDiscoverySnippet.start();

        //TODO
        //create a new OPCDevice with structure methods - common to all OPCcommunication?
       
        

       
    }//GEN-LAST:event_btn_start_discoveryActionPerformed

    private void LDSRegisterserverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LDSRegisterserverActionPerformed
        int ret = 0;
        try {
            ret = opcuaServerInstanceMILO.register(LDSserverAddress.getText());
            opc_comms_log.append("Registering MSB OPCUA Milo Server on the LDS server...\n");
            if (ret == 1) {
                opc_comms_log.append("Success\n");
                setConnectionColor(true, false, OnOffRegister, labelRegister);
            } else {
                opc_comms_log.append("Failed to register MSB OPCUA Milo Server on the LDS server!\n");
                setConnectionColor(false, true, OnOffRegister, labelRegister);
            }
        } catch (Exception ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_LDSRegisterserverActionPerformed

    private void LDSserverAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LDSserverAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LDSserverAddressActionPerformed

    private void StartMSBServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartMSBServerActionPerformed
        try {
            // TODO add your handling code here:
            // TODO add your handling code here:
            opc_comms_log.append("Starting MSB OPCUA Milo Server...\n");

            opcuaServerInstanceMILO = new opcuaServerMSB(msb_opcua_servername.getText()); //new MSB Milo server
            //launch MILO MSB OPCUA Server endpoint
            if (opcuaServerInstanceMILO.control == false) {
                try {
                    opcuaServerInstanceMILO.startup().get();
                    opc_comms_log.append("Server created. \n");
                    setConnectionColor(true,false, OnOffServerPanel, labelServer);
                    /*final CompletableFuture<Void> future = new CompletableFuture<>();
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> future.complete(null)));
                    future.get();*/
                } catch (Exception ex) {
                    Logger.getLogger(FabioMSB_Struct.class.getName()).log(Level.SEVERE, null, ex);
                    opc_comms_log.append("Exception: " + ex);
                    setConnectionColor(false,true, OnOffServerPanel, labelServer);
                }
            } else {
                System.out.println("MSB Server already created!\n");
                opc_comms_log.append("MSB Server already created!\n");
            }

            try {//test to see if the server is running at the first discovery
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

        } catch (Exception ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_StartMSBServerActionPerformed

    private void StartMSBServerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StartMSBServerMouseClicked

    }//GEN-LAST:event_StartMSBServerMouseClicked

    private void msb_opcua_servernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msb_opcua_servernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_msb_opcua_servernameActionPerformed

    private void StartWebServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartWebServiceActionPerformed
        // TODO add your handling code here:
        String address = WebServiceAddress.getText();
  
        //myRecipeWS.publish(address, new RecipesDeployerImpl());
        //System.out.println("Listening: " + address);
        //opc_comms_log.setText("Starting WebServer...\n Listening on: "+ address);
        
        Thread wsThread = new Thread() {
            public void run() {
                
                Endpoint myRecipeWS = Endpoint.publish(address, new RecipesDeployerImpl());
                System.out.println("Listening: " + address);
                opc_comms_log.append("Starting WebServer...\nListening on: " + address +"\n");
                Boolean status=myRecipeWS.isPublished();
                if (status) {
                    setConnectionColor(true, false, OnOffWSPanel, labelWS);
                    opc_comms_log.append("Recipes WebServer successfully published\n");
                } else {
                    setConnectionColor(false, true, OnOffWSPanel, labelWS);
                    opc_comms_log.append("Failed publishing Recipes WebServer\n");
                }
            }
        };

        wsThread.start();
    }//GEN-LAST:event_StartWebServiceActionPerformed

    private void WebServiceAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WebServiceAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_WebServiceAddressActionPerformed

    private void btn_SendURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SendURLActionPerformed
        String deviceName = String.valueOf(comboServers.getSelectedItem());
        //ver hashmap e chamar o metodo
        MyHashMaps myOpcUaClientsMap = MyHashMaps.getInstance(); //singleton to access client objects in other classes
        MSB_MiloClientSubscription MSBcs = myOpcUaClientsMap.getOPCclientIDMaps().get(deviceName);
        
        MSBcs.SendServerURL(MSBcs.milo_client_instanceMSB, MSB_OPCUA_SERVER_ADDRESS).exceptionally(ex -> {
            System.out.println("error invoking SendServerURL() for server: " + deviceName + "\n" + ex); //logger.error("error invoking SendServerURL()", ex);
            opc_comms_log.append("error invoking SendServerURL() for server: " + deviceName + "\n" + ex + "\n");
            return "-1.0";
        }).thenAccept(v -> {
            //logger.info("SendServerURL(cenas)={}", v);
            System.out.println("SendServerURL(uri)={}\n" + v);
            opc_comms_log.append("SendServerURL(uri)={}\n" + v);
            //future.complete(client);
        });

    }//GEN-LAST:event_btn_SendURLActionPerformed

    private void btn_sendRecipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendRecipeActionPerformed
        String deviceName = String.valueOf(comboServers.getSelectedItem());
        //ver hashmap e chamar o metodo
        MyHashMaps myOpcUaClientsMap = MyHashMaps.getInstance(); //singleton to access client objects in other classes
        MSB_MiloClientSubscription MSBcs = myOpcUaClientsMap.getOPCclientIDMaps().get(deviceName);

        MSBcs.SendRecipetoDevice(MSBcs.milo_client_instanceMSB, textToSend.getText()).exceptionally(ex -> {
            System.out.println("error invoking SendRecipetoDevice() for server: " + deviceName + "\n" + ex); //logger.error("error invoking SendServerURL()", ex);
            opc_comms_log.append("error invoking SendRecipetoDevice() for server: " + deviceName + "\n" + ex + "\n");
            return "-1.0";
        }).thenAccept(v -> {
            //logger.info("SendServerURL(cenas)={}", v);
            System.out.println("SendRecipetoDevice(uri)={}\n" + v);
            opc_comms_log.append("SendRecipetoDevice(uri)={}\n" + v);
            //future.complete(client);
        });

    }//GEN-LAST:event_btn_sendRecipeActionPerformed

    private void comboServersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboServersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboServersActionPerformed

    private void btn_RequestProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RequestProductActionPerformed
        //OLD REQUEST PRODUCT - handled by adapter

        /*String deviceName = String.valueOf(comboServers.getSelectedItem());
        //ver hashmap e chamar o metodo
        MyHashMaps myMaps = MyHashMaps.getInstance(); //singleton to access client objects in other classes
        MSB_MiloClientSubscription MSBcs = myMaps.getOPCclientIDMaps().get(deviceName);
        
        MSBcs.RequestProduct(MSBcs.milo_client_instanceMSB, textToSend.getText()).exceptionally(ex -> {
            System.out.println("error invoking RequestProduct() for server: " + deviceName + "\n" + ex); //logger.error("error invoking SendServerURL()", ex);
            opc_comms_log.append("error invoking RequestProduct() for server: " + deviceName + "\n" + ex + "\n");
            return "-1.0";
        }).thenAccept(v -> {
            //logger.info("SendServerURL(cenas)={}", v);
            System.out.println("RequestProduct(uri)={}\n" + v);
            opc_comms_log.append("RequestProduct(uri)={}\n" + v);
            //future.complete(client);
        });*/
        
        
        //NEW REQUEST PRODUCT
        MyHashMaps myMaps = MyHashMaps.getInstance(); //singleton to access client objects in other classes
        String requestedProduct=textToSend.getText();
        
        MSB_MiloClientSubscription MSBcs2 = myMaps.ProductIDAdapterMaps.get(requestedProduct); //get a clientObject that can produce ProductID
        List<String> DeviceNames = new ArrayList<String>();

        for (String key : myMaps.OPCclientIDMaps.keySet()) { //get workstation names capable of produce ProductID
            MSB_MiloClientSubscription MCS = MyHashMaps.OPCclientIDMaps.get(key);
            
            String clientInstanceObject = MCS.milo_client_instanceMSB.toString();
            String clientCapableOfProduct = MSBcs2.milo_client_instanceMSB.toString();
                    
            if (clientInstanceObject.equals(clientCapableOfProduct)) {
                DeviceNames.add(key);
            }
        }
        
        if (DeviceNames.size() > 0) {
            List<ExecuteData> ExeData = myMaps.ExecutiontTableMaps.get(DeviceNames.get(0)); //choose the first one - Warning: in the future, choose the most cost-effective workstation
            
            for(int i=0;i<ExeData.size();i++){
                String prodID = ExeData.get(i).productID;
                String methodIdSTR = ExeData.get(i).methodID;
                String objectIdSTR = ExeData.get(i).objectID;
                
                 NodeId objectId = NodeId.parse("ns=2;s="+objectIdSTR);
                 NodeId methodId = NodeId.parse("ns=2;s="+methodIdSTR);
        
                
                if(prodID.equals(requestedProduct)){
                    MSBcs2.InvoqueDeviceSkill(MSBcs2.milo_client_instanceMSB, objectId, methodId);
                    break;
                }
            }
            
            
        } else {
            opc_comms_log.append("There is no Workstation capable of making product: " + textToSend.getText() + "\n");
        }
        
        
    }//GEN-LAST:event_btn_RequestProductActionPerformed

    private void btn_DeviceRegistrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeviceRegistrationActionPerformed
       String ret="null";
       //OPCDeviceItf DeviceITF = new OPCDeviceItf(); 
        try {
           ret=DeviceITF.AllCases("deviceregistration", textToSend.getText()); //simulate a device registration
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call device registration method: "+ex+"\n");
        } catch (SAXException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
           opc_comms_log.append("Failed to call device registration method: "+ex+"\n");
        } catch (IOException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call device registration method: "+ex+"\n");
        } catch (JAXBException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call device registration method: "+ex+"\n");
        } catch (TransformerException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        opc_comms_log.append("Device registration method called. Returned: "+ret+"\n");
    }//GEN-LAST:event_btn_DeviceRegistrationActionPerformed

    private void btn_ChangedStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChangedStateActionPerformed
        String ret = "null";
        //OPCDeviceItf DeviceITF = new OPCDeviceItf();

        try {
            ret = DeviceITF.AllCases("changedstate", textToSend.getText()); //simulate a device registration
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call changed state method: "+ex+"\n");
        } catch (SAXException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call changed state method: "+ex+"\n");
        } catch (IOException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call changed state method: "+ex+"\n");
        } catch (JAXBException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call changed state method: "+ex+"\n");
        } catch (TransformerException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        opc_comms_log.append("Changed State method called. Returned: "+ret+"\n");
    }//GEN-LAST:event_btn_ChangedStateActionPerformed

    private void btn_RecipeExecutionDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RecipeExecutionDoneActionPerformed
        String ret="null";
       //OPCDeviceItf DeviceITF = new OPCDeviceItf(); 
        try {
           ret=DeviceITF.AllCases("recipeexecutiondone", textToSend.getText()); //simulate a device registration
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call RecipeExecutionDone method: "+ex+"\n");
        } catch (SAXException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
           opc_comms_log.append("Failed to call RecipeExecutionDone method: "+ex+"\n");
        } catch (IOException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call RecipeExecutionDone method: "+ex+"\n");
        } catch (JAXBException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call RecipeExecutionDone method: "+ex+"\n");
        } catch (TransformerException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        opc_comms_log.append("RecipeExecutionDone method called. Returned: "+ret+"\n");
    }//GEN-LAST:event_btn_RecipeExecutionDoneActionPerformed

    private void btn_sendrecipe2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendrecipe2ActionPerformed
        String ret="null";
       //OPCDeviceItf DeviceITF = new OPCDeviceItf(); 
        try {
           ret=DeviceITF.AllCases("sendRecipe", textToSend.getText()); //simulate a sendRecipe
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call sendRecipe method: "+ex+"\n");
        } catch (SAXException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
           opc_comms_log.append("Failed to call sendRecipe method: "+ex+"\n");
        } catch (IOException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call sendRecipe method: "+ex+"\n");
        } catch (JAXBException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call sendRecipe method: "+ex+"\n");
        } catch (TransformerException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        opc_comms_log.append("sendRecipe method called. Returned: "+ret+"\n");
    }//GEN-LAST:event_btn_sendrecipe2ActionPerformed

    private void btn_updatestatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updatestatusActionPerformed
       String ret="null";
       //OPCDeviceItf DeviceITF = new OPCDeviceItf(); 
        try {
           ret=DeviceITF.AllCases("statusupdate", textToSend.getText()); //simulate a device registration
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call statusupdate method: "+ex+"\n");
        } catch (SAXException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
           opc_comms_log.append("Failed to call statusupdate method: "+ex+"\n");
        } catch (IOException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call statusupdate method: "+ex+"\n");
        } catch (JAXBException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
            opc_comms_log.append("Failed to call statusupdate method: "+ex+"\n");
        } catch (TransformerException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        opc_comms_log.append("statusupdate method called. Returned: "+ret+"\n");
    }//GEN-LAST:event_btn_updatestatusActionPerformed

    private void btn_invoqueMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_invoqueMethodActionPerformed
        String deviceName = String.valueOf(comboServers.getSelectedItem());
        //ver hashmap e chamar o metodo
        MyHashMaps myOpcUaClientsMap = MyHashMaps.getInstance(); //singleton to access client objects in other classes
        MSB_MiloClientSubscription MSBcs = myOpcUaClientsMap.getOPCclientIDMaps().get(deviceName);
        
        //TESTE INVOQUESKILL
        NodeId objectId = NodeId.parse("ns=2;s=Pre-Demonstrator_InstanceHierarchy/AssemblySystem/WorkStation/SC1:Task_slow_Recipe");
        NodeId methodId = NodeId.parse("ns=2;s=InvokeSkill/SC1:Task_slow_Recipe");
        
        
        MSBcs.InvoqueDeviceSkill(MSBcs.milo_client_instanceMSB, objectId, methodId).exceptionally(ex -> {
            System.out.println("error invoking Call InvoqueMethod() for server: "+ ex); //logger.error("error invoking SendServerURL()", ex);
            return "-1.0";
        }).thenAccept(v -> {
            //logger.info("SendServerURL(cenas)={}", v);
            System.out.println("Call InvoqueMethod(uri)={}\n" + v);
            //future.complete(client);
        });
    }//GEN-LAST:event_btn_invoqueMethodActionPerformed

    private void prodBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prodBActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        //NEW REQUEST PRODUCT
        MyHashMaps myMaps = MyHashMaps.getInstance(); //singleton to access client objects in other classes
        String requestedProduct="AGV_B";
        
        MSB_MiloClientSubscription MSBcs2 = myMaps.ProductIDAdapterMaps.get(requestedProduct); //get a clientObject that can produce ProductID
        List<String> DeviceNames = new ArrayList<String>();

        for (String key : myMaps.OPCclientIDMaps.keySet()) { //get workstation names capable of produce ProductID
            MSB_MiloClientSubscription MCS = MyHashMaps.OPCclientIDMaps.get(key);
            
            String clientInstanceObject = MCS.milo_client_instanceMSB.toString();
            String clientCapableOfProduct = MSBcs2.milo_client_instanceMSB.toString();
                    
            if (clientInstanceObject.equals(clientCapableOfProduct)) {
                DeviceNames.add(key);
            }
        }
        
        if (DeviceNames.size() > 0) {
            List<ExecuteData> ExeData = myMaps.ExecutiontTableMaps.get(DeviceNames.get(0)); //choose the first one - Warning: in the future, choose the most cost-effective workstation
            
            for(int i=0;i<ExeData.size();i++){
                String prodID = ExeData.get(i).productID;
                String methodIdSTR = ExeData.get(i).methodID;
                String objectIdSTR = ExeData.get(i).objectID;
                
                 NodeId objectId = NodeId.parse("ns=2;s="+objectIdSTR);
                 NodeId methodId = NodeId.parse("ns=2;s="+methodIdSTR);
        
                
                if(prodID.equals(requestedProduct)){
                    MSBcs2.InvoqueDeviceSkill(MSBcs2.milo_client_instanceMSB, objectId, methodId);
                    opc_comms_log.append("Invoked prodB \n");
                    break;
                }
            }
            
            
        } else {
            opc_comms_log.append("There is no Workstation capable of making product: " + textToSend.getText() + "\n");
        }
        
    }//GEN-LAST:event_prodBActionPerformed

    private void prodAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prodAActionPerformed
        // TODO add your handling code here:
        //NEW REQUEST PRODUCT
        MyHashMaps myMaps = MyHashMaps.getInstance(); //singleton to access client objects in other classes
        String requestedProduct="AGV_A";
        
        MSB_MiloClientSubscription MSBcs2 = myMaps.ProductIDAdapterMaps.get(requestedProduct); //get a clientObject that can produce ProductID
        List<String> DeviceNames = new ArrayList<String>();

        for (String key : myMaps.OPCclientIDMaps.keySet()) { //get workstation names capable of produce ProductID
            MSB_MiloClientSubscription MCS = MyHashMaps.OPCclientIDMaps.get(key);
            
            String clientInstanceObject = MCS.milo_client_instanceMSB.toString();
            String clientCapableOfProduct = MSBcs2.milo_client_instanceMSB.toString();
                    
            if (clientInstanceObject.equals(clientCapableOfProduct)) {
                DeviceNames.add(key);
            }
        }
        
        if (DeviceNames.size() > 0) {
            List<ExecuteData> ExeData = myMaps.ExecutiontTableMaps.get(DeviceNames.get(0)); //choose the first one - Warning: in the future, choose the most cost-effective workstation
            
            for(int i=0;i<ExeData.size();i++){
                String prodID = ExeData.get(i).productID;
                String methodIdSTR = ExeData.get(i).methodID;
                String objectIdSTR = ExeData.get(i).objectID;
                
                 NodeId objectId = NodeId.parse("ns=2;s="+objectIdSTR);
                 NodeId methodId = NodeId.parse("ns=2;s="+methodIdSTR);
        
                
                if(prodID.equals(requestedProduct)){
                    MSBcs2.InvoqueDeviceSkill(MSBcs2.milo_client_instanceMSB, objectId, methodId);
                    opc_comms_log.append("Invoked prodA \n");
                    break;
                }
            }
            
            
        } else {
            opc_comms_log.append("There is no Workstation capable of making product: " + textToSend.getText() + "\n");
        }
        
    }//GEN-LAST:event_prodAActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Get server name from the combobox
        String deviceName = String.valueOf(comboServers.getSelectedItem());
        //get the client for that device adapter server
         MyHashMaps myMaps = MyHashMaps.getInstance(); //singleton to access client objects in other classes
         MSB_MiloClientSubscription MSBcs2 = myMaps.OPCclientIDMaps.get(deviceName); //get a clientObject that can produce ProductID
         NodeId objectId = NodeId.parse("ns=0;i=85");
         NodeId methodId = NodeId.parse("ns=2;s="+"InvokeSkill/leakTestSkill");
         MSBcs2.InvoqueDeviceSkill(MSBcs2.milo_client_instanceMSB, objectId, methodId);
         
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private static String registerServer(java.lang.String serverName, java.lang.String serverUri) {
        //msbweb.RegisterDevice_Service service = new msbweb.RegisterDevice_Service();
        //msbweb.DBInteraction port = service.getDBInteractionPort();
        //return port.registerServer(serverName, serverUri);*/
        DeviceRegistryBean dbMSB = new DeviceRegistryBean();
        String res=dbMSB.register_device(serverName, "null", "null", serverUri, "opcua");
        System.out.println(res);
                
       return res;
    }
    
     public static int RemoveDownServer(String ServerName) {

        MyHashMaps myOpcUaClientsMap = MyHashMaps.getInstance(); //singleton to access client objects in other classes
        myOpcUaClientsMap.deleteOPCclientIDMaps(ServerName); //remove server from singleton Hashmap
        OPCclientIDMap.remove(ServerName); //remove server from HashMap
        //delete server from DB
        DeviceRegistryBean dbMSB = new DeviceRegistryBean();
        int res = dbMSB.deregister_device(ServerName);

        if (res != -999) {
            System.out.println("DownServer successfully deleted from DB!");
            return 1;
        } else {
            System.out.println("ERROR deleting DownServer from DB!");
            return -1;
        }

    }
     
    private void addToTableServers(String serverName, String protocol, String serverUri) {
        TServersmodel.addRow(new Object[]{serverName, protocol, serverUri});

        Object[] rowData = new Object[TServersmodel.getColumnCount()];
        for (int i = 0; i < TServersmodel.getColumnCount(); i++) {
            rowData[i] = TServersmodel.getValueAt(0, i);
            System.out.println("SERVER NA TABELA: " + rowData.toString());
        }
        opc_comms_log.append("Server successfully added to table. Name: " + serverName + "URL: " + serverUri + "\n");
        
        //warning - demo purposes
        if(serverName.contains("Adapter")){
            CreateProductButtons();
        }

    }
    
    public static void FillProductsTable() {
        MyHashMaps myOpcMap = MyHashMaps.getInstance(); //singleton to access hashmaps in other classes
        int index=0;
        for (String key : myOpcMap.ExecutiontTableMaps.keySet()) {
            System.out.println(key + " ->MAPA de execução MSBGUI<- - " + MyHashMaps.ExecutiontTableMaps.get(key));
            List<ExecuteData> ETD = myOpcMap.ExecutiontTableMaps.get(key); //get product list for each workstatio 
            for (int i = 0; i < ETD.size(); i++) {
                addToTableProduct(ETD.get(i).productID, ETD.get(i).recipeID, ETD.get(i).status, key); //add each product from the list for each workstation
            }
            index++;
        }   
        
       
    }
    
    public void CreateProductButtons(){
         //DRAFT 
        try {
            prodA.show();
            prodB.show();
            Image imgA = ImageIO.read(new File("images\\prodA.png"));
            Image imgB = ImageIO.read(new File("images\\prodB.png"));
            
            prodA.setIcon(new ImageIcon(imgA));
            prodB.setIcon(new ImageIcon(imgB));
        } catch (Exception ex) {
            System.out.println(ex);
        } 
    }
    
    public static void FillDevicesTable() {
        for (String key : MyHashMaps.ServerTableMaps.keySet()) {
            System.out.println(key + " ->MAPA de execução MSBGUI<- - " + MyHashMaps.ServerTableMaps.get(key));
            ServerStatus SStat = MyHashMaps.ServerTableMaps.get(key);
            //call mainwindow filltables

            addToTableDevice(SStat.Name, SStat.Connected, SStat.URL, key);
        }
    }
    
    
    //add a row to tableProduct
    public static void addToTableProduct(String productID, String RecipeID, Boolean Status, String Workstation) {
        TProductsmodel.addRow(new Object[]{productID, RecipeID, Status, Workstation});

        Object[] rowData = new Object[TProductsmodel.getColumnCount()];
        for (int i = 0; i < TProductsmodel.getColumnCount(); i++) {
            rowData[i] = TProductsmodel.getValueAt(0, i);
            System.out.println("Product NA TABELA: " + rowData[i].toString());
        }
        opc_comms_log.append("Product successfully added to table. Name: " + productID + "\n");
    }
    
       //add a row to DeviceTable
    public static void addToTableDevice(String DeviceName, Boolean Status, String Endpoint, String WorkStation) {
        TDevicesmodel.addRow(new Object[]{DeviceName, Status, Endpoint, WorkStation});
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object[] rowData = new Object[TDevicesmodel.getColumnCount()];
        for (int i = 0; i < TDevicesmodel.getColumnCount(); i++) {
            rowData[i] = TDevicesmodel.getValueAt(0, i);
            System.out.println("Devices NA TABELA: " + rowData[i].toString());
        }
        opc_comms_log.append("Devices successfully added to table. Name: " + DeviceName + "\n");
    }

    private void deleteFromTableServers(String serverName, String protocol, String serverUri) {

        int rowcont = TServersmodel.getRowCount();
        int indexToRemove = -1;
        for (int i = 0; i < TServersmodel.getRowCount(); i++) {//For each row
            for (int j = 0; j < TServersmodel.getColumnCount(); j++) {//For each column in that row
                if (TServersmodel.getValueAt(i, j).equals(serverName)) {//Search the model
                    System.out.println("FOUND SERVER TO DELETE from table: " + TServersmodel.getValueAt(i, j));//Print if found string
                    indexToRemove = i;
                    TServersmodel.removeRow(indexToRemove);
                    i--;
                }
            }//For inner loop 
        }//For outer loop 

        if (indexToRemove != -1) {
           
            System.out.println("SERVER DELETED from table: " + serverName);//Print if server found 
            opc_comms_log.append("SERVER DELETED from table: " + serverName + "\n");
        } else {
            System.out.println("SERVER TO DELETE NOT FOUND from table: " + serverName);//Print if server not found 
            //opc_comms_log.append("SERVER TO DELETE NOT FOUND from table: " + serverName + "\n");
        }

        comboServers.removeItem(serverName);

    }
    
    private static void deleteFromTableProducts(String productID, String recipeID, String workstationName) {
        //delete from product ID? recipeID? or workstationName? or all?
        //TODO

        int rowcont=TProductsmodel.getRowCount();
        int indexToRemove = -1;
        for (int i = 0; i < TProductsmodel.getRowCount(); i++) {//For each row
            for (int j = 0; j < TProductsmodel.getColumnCount(); j++) {//For each column in that row
                if (/*TProductsmodel.getValueAt(i, j).equals(productID) || TProductsmodel.getValueAt(i, j).equals(recipeID) || */TProductsmodel.getValueAt(i, j).equals(workstationName)) {//Search the model
                    System.out.println("FOUND PRODUCT TO DELETE from table: " + TProductsmodel.getValueAt(i, j)+ " at row:"+i +" col:"+j);//Print if found string
                    indexToRemove = i;
                    TProductsmodel.removeRow(indexToRemove);
                    i--;
                }
            }//For inner loop 
        }//For outer loop 

        if (indexToRemove != -1) {
            System.out.println("PRODUCT DELETED from table: " + productID);//Print if server found 
            opc_comms_log.append("PRODUCT DELETED from table: " + productID + "\n");
        } else {
            System.out.println("PRODUCT TO DELETE NOT FOUND from table: " + productID);//Print if server not found 
            //opc_comms_log.append("PRODUCT TO DELETE NOT FOUND from table: " + productID + "\n");
        }

    }
    
    private static void deleteFromTableDevices(String DeviceName, Boolean status, String endpoint, String workstationName) {
        //delete from DeviceName? status? or endpoint? or all?
        //TODO

        int rowcount=TDevicesmodel.getRowCount();
       
        int indexToRemove = -1;
        for (int i = 0; i < TDevicesmodel.getRowCount(); i++) {//For each row
            for (int j = 0; j < TDevicesmodel.getColumnCount(); j++) {//For each column in that row
                if (/*TDevicesmodel.getValueAt(i, j).equals(DeviceName) || TDevicesmodel.getValueAt(i, j).equals(endpoint) || */TDevicesmodel.getValueAt(i, j).equals(workstationName)) {//Search the model
                    System.out.println("FOUND PRODUCT TO DELETE from table: " + TDevicesmodel.getValueAt(i, j)+ " at row:"+i +" col:"+j);//Print if found string
                    indexToRemove = i;
                    TDevicesmodel.removeRow(indexToRemove);
                    i--;
                }
            }//For inner loop 
        }//For outer loop 

        if (indexToRemove != -1) { 
            System.out.println("DEVICE DELETED from table: " + workstationName);//Print if server found 
            opc_comms_log.append("DEVICE DELETED from table: " + workstationName + "\n");
        } else {
            System.out.println("DEVICE TO DELETE NOT FOUND from table: " + workstationName);//Print if server not found 
            //opc_comms_log.append("DEVICE TO DELETE NOT FOUND from table: " + DeviceName + "\n");
        }

    }

      private void setImage() {
        //faz o set das images e dimensiona se acordo com o tamanho do panel
        BufferedImage img = null;
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        try {
            img = ImageIO.read(new File("images\\green-circle.png"));
            img1 = ImageIO.read(new File("images\\red.png"));
            img2 = ImageIO.read(new File("images\\glossy-gray.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = img.getScaledInstance(34, 31, Image.SCALE_SMOOTH);
        Image dimg2 = img1.getScaledInstance(34, 31, Image.SCALE_SMOOTH);
        Image dimg3 = img2.getScaledInstance(34, 31, Image.SCALE_SMOOTH);
        greenLight = new ImageIcon(dimg);
        redLight = new ImageIcon(dimg2);
        greyLight= new ImageIcon(dimg3);
    }


    private boolean setConnectionColor(boolean con, boolean lastState, JPanel panel, JLabel label) {
        // verifica o estado da ligaçao ao servidor e altera a imagem de acordo com a mesma
        if (con != lastState) {

            if (con == true) {

                panel.removeAll();
                label.setIcon(greenLight);
                panel.add(label);
                panel.revalidate();
                panel.repaint();
            } else if (con == false) {

                panel.removeAll();
                label.setIcon(redLight);
                panel.add(label);
                panel.revalidate();
                panel.repaint();
            }
            return con;
        }
        return lastState;
    }
    
      private void threadCheckServersConnection() {
        //thread da verificaçao da ligaçao ao servidor do plc
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                /*boolean aux1 = setConnectionColor(SubscriptionPLC.Connection, SubscriptionPLC.lastState, OnOffPLCPanel, labelPLC);
                if (aux1 == SubscriptionPLC.Connection) {
                    SubscriptionPLC.lastState = aux1;
                }*/
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }
      
      //reimplementation of modellistener for checkbox value checking
    public class CheckBoxModelListener implements TableModelListener {

        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 2) { //column of the checkbox
                TableModel model = (TableModel) e.getSource();
                String columnName = model.getColumnName(column);
                Boolean checked = (Boolean) model.getValueAt(row, column);
                String recipeID = (String) model.getValueAt(row, 1);
                if (checked) {
                    System.out.println(columnName + ": " + true);
                    opc_comms_log.append("SendChanges:" + columnName + " of " + recipeID + " " + true + "\n");
                } else {
                    System.out.println(columnName + ": " + false);
                    opc_comms_log.append("SendChanges:" + columnName + " of " + recipeID + " " + false + "\n");
                }
            }
        }
    }
    
    public void setLogText(String text){
        opc_comms_log.append(text + '\n');
    }
    
    public String CleanTablesFromWorkstation(String WorkstationName) {
        deleteFromTableDevices(null, null, null, WorkstationName);
        deleteFromTableProducts(null, null, WorkstationName);
        deleteFromTableServers(WorkstationName,null, null);
        
        return "OK";
    }
    
   
    public String CleanHashMapsFromWorkstation(String WorkstationName) {
        MyHashMaps myMaps = MyHashMaps.getInstance(); //singleton to access hashmaps in other classes

        MSB_MiloClientSubscription ret=null;
        CyberPhysicalAgentDescription cpadRET=null;
        Map<String, MSB_MiloClientSubscription> OpcuaDeviceHashMap = myMaps.getOPCclientIDMaps(); //get opcdevice name and object map 
        MSB_MiloClientSubscription ClientToRemove = OpcuaDeviceHashMap.get(WorkstationName);

        //remove every trace related to the opc client object name on the productIDAdapterMaps
        for (String key : myMaps.ProductIDAdapterMaps.keySet()) {
            MSB_MiloClientSubscription MCS = myMaps.ProductIDAdapterMaps.get(key);

            String clientInstanceObject = MCS.milo_client_instanceMSB.toString();

            if (clientInstanceObject.equals(ClientToRemove)) {
                ret=myMaps.ProductIDAdapterMaps.remove(key);
                cpadRET = myMaps.AgentDeviceMaps.remove(clientInstanceObject);
            }
        }

        //remove every trace related to the workstation name on the ExecutiontTableMaps
        List <ExecuteData> ret1 = myMaps.ExecutiontTableMaps.remove(WorkstationName);

        //remove every trace related to the workstation name on the ServerTableMaps
        ServerStatus ret2 = myMaps.ServerTableMaps.remove(WorkstationName);

        //remove every trace related to the workstation name on the OpcuaDeviceHashMap
        MSB_MiloClientSubscription ret3 = OpcuaDeviceHashMap.remove(WorkstationName);
        
        if(ret!=null || ret1!=null || ret2!=null || ret3!=null) //if there was data removed, notify
        opc_comms_log.append("Successfully removed " + WorkstationName + " from the Maps" + "\n");
        
        
        /*AgentStatus status = DeviceITF.RemoveAgent(cpadRET.getUniqueName());
        opc_comms_log.append("Removing agent: "+cpadRET.getUniqueName());
        if(status.equals("OK")){
            opc_comms_log.append("Agent successfully deleted");
        }else{
            opc_comms_log.append("Problem deleting agent!");
        }*/

        return "OK";
    }

     
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MSB_gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MSB_gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MSB_gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MSB_gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    new MSB_gui().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(MSB_gui.class.getName()).log(Level.SEVERE, null, ex);
                }

                ImageIcon imageIcon = new ImageIcon(new ImageIcon("C:\\Users\\fabio.miranda\\Documents\\NetBeansProjects\\openMOSMSB\\images\\OpenMos-logo-RGB-colours.png").getImage().getScaledInstance(openmoslogo.getWidth(), openmoslogo.getHeight(), Image.SCALE_DEFAULT));
                openmoslogo.setIcon(imageIcon);
                
                jScrollPane1.getVerticalScrollBar().setAutoscrolls(true);
                
                DefaultCaret caret = (DefaultCaret)opc_comms_log.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

            }
        });
        
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboMSB;
    private javax.swing.JTable DevicesTable;
    private javax.swing.JButton LDSRegisterserver;
    private javax.swing.JTextField LDSserverAddress;
    private javax.swing.JPanel OnOffLDS;
    private javax.swing.JPanel OnOffRegister;
    private javax.swing.JPanel OnOffServerPanel;
    private javax.swing.JPanel OnOffWSPanel;
    private javax.swing.JTable ProductsTable;
    private javax.swing.JButton StartMSBServer;
    private javax.swing.JButton StartWebService;
    private javax.swing.JTable TableServers;
    private javax.swing.JTextField WebServiceAddress;
    private javax.swing.JButton btn_ChangedState;
    private javax.swing.JButton btn_DeviceRegistration;
    private javax.swing.JButton btn_RecipeExecutionDone;
    private javax.swing.JButton btn_RequestProduct;
    private javax.swing.JButton btn_SendURL;
    private javax.swing.JButton btn_invoqueMethod;
    private javax.swing.JButton btn_sendRecipe;
    private javax.swing.JButton btn_sendrecipe2;
    private javax.swing.JButton btn_start_discovery;
    private javax.swing.JButton btn_updatestatus;
    private java.awt.Choice choice1;
    private java.awt.Choice choice2;
    private java.awt.Choice choice3;
    private javax.swing.JComboBox<String> comboServers;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private static javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField msb_opcua_servername;
    private static javax.swing.JTextArea opc_comms_log;
    private static javax.swing.JLabel openmoslogo;
    private static javax.swing.JButton prodA;
    private static javax.swing.JButton prodB;
    private javax.swing.JTextField textToSend;
    // End of variables declaration//GEN-END:variables
}
