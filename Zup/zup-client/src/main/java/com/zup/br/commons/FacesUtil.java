/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.commons;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.context.RequestContext;

/**
 *
 * @author mateusgobo
 */
public abstract class FacesUtil {

    protected String header;
    
    private UIComponent component;
    private String areaAtiva;
    private String errorMessage;
    private Map<Class, Object> instances;
    
    public List<UIComponent> getTreeFaces(){
        return FacesContext.getCurrentInstance().getViewRoot().getChildren();
    }
    
    /**
     * Método da classe - Encontrando componentes na árvore faces
     * (USO - CommandLink commandAlterar = (CommandLink) findFacesComponent(new String[]{"elementos até o componente desejado"}))
     * @param roots - Id´s que devem ser localizados
     * @return Retorna UIComponent em caso de sucesso e null se não encontrar
     */
    public UIComponent findFacesComponent(String[] roots) {
        int passed = 0;
        UIComponent myComponent = null;
        for (String root : roots) {
            myComponent = passed == 0 ? FacesContext.getCurrentInstance().getViewRoot().findComponent(root) : myComponent.findComponent(root);
            if(myComponent != null){
                passed = 1;            
            }
        }
        return myComponent;
    }
    
    public UIComponent searchFacesComponent(List<UIComponent> components, String idComponente){
        for(UIComponent c : components){
            if(idComponente.equals(c.getId())){
                this.component = c;
                return component;
            }else{
                if(component == null){
                    if(!c.getChildren().isEmpty()){
                        searchFacesComponent(c.getChildren(), idComponente);
                    }
                }else{
                    return component;
                }
            }
        }
        return null;
    }
    
    /**
     * Método da classe - Recuperando parametros de um componente de tela
     * @param rootsComponent
     * @return 
     */
    public List<UIComponent> findFacesParameter(String[] rootsComponent){        
        List<UIComponent> myParameters = new ArrayList<>();
        UIComponent comp = findFacesComponent(rootsComponent);        
        for(UIComponent children : comp.getChildren()){
            if(children instanceof UIParameter){                
                myParameters.add(children);            
            }                
        }
        return myParameters;
    }
    
    /**
     * Método da classe - Adicionando parametros ao um component
     * @param component - Componente que receberá os parametros de tela
     * @param parameters - Parametros a serem agregados ao componente
     * @return O componente com os parametros
     */
    public UIComponent addParameterForComponent(UIComponent component, List<UIComponent> parameters){        
        if(parameters != null && !parameters.isEmpty()){
            for(UIComponent parameter : parameters){
                component.getChildren().add(parameter);
            }
        }
        return component;
    }
    
    /**
     * Método da classe - Delegando ação para component
     * Uso - delegateActionComponent(localizacaoComponente(findFacesComponent(path)), "#{bean.metodo}");
     * @param pathComponent = Caminho do componente na árvore de componentes do primefaces / jsf
     * @param action = Listener definido na propriedade action do componente. Ex.: #{Bean.method}
     * @return true se sucesso e false se falha
     */
    public boolean delegateActionComponent(String[] pathComponent, String action){
        try{
            UIComponent comp = findFacesComponent(pathComponent);
            MethodExpression method = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), action, null, new Class<?>[0]);        
            if (comp.getClass().getCanonicalName().compareTo("org.primefaces.component.commandbutton.CommandButton") == 0) {
                ((CommandButton) comp).setActionExpression(method);
            } else if (comp.getClass().getCanonicalName().compareTo("org.primefaces.component.commandbutton.CommandLink") == 0) {
                ((CommandLink) comp).setActionExpression(method);
            } 
            return true;
        }catch(Exception e){
            Logger.getLogger(FacesUtil.class.getSimpleName()).log(Level.SEVERE,"Erro na execução do método delegateActionComponent(...): ",e);
            return false;
        }
    }            
    
    public MethodExpression generateMethodExpressionRuntime(String expression){
        return FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), expression, null, new Class<?>[0]);        
    }    
    public ValueExpression generateExpressionValueRuntime(String expression, Class clazzTypeData){
        return FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(FacesContext.getCurrentInstance().getELContext(), expression, clazzTypeData);
    }
    public void exibirMensagem(FacesMessage.Severity severity, String title, String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, title, msg));
    }
    
    /**
     * Método da classe - Navegando entre os diretórios
     * @param root - Caminho do arquivo
     * @param header - Título da página
     */
    protected void page(String root, String header) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(root + ".jsf");
        } catch (IOException ex) {
            Logger.getLogger(FacesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("rawtypes")
    public Object getSessionBean(Class clazzBean) {
        Object sessionBean = null;
        if (clazzBean != null) {
            Annotation[] annotation = clazzBean.getDeclaredAnnotations();
            for (Annotation a : annotation) {
                if (a.annotationType().equals(ManagedBean.class)) {
                    sessionBean = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(((ManagedBean) clazzBean.getDeclaredAnnotations()[0]).name());
                }
            }
        }
        if (sessionBean == null) {
            exibirMensagem(FacesMessage.SEVERITY_FATAL, "Informação", "Erro interno no servidor, por favor entre em contato com o suporte!");
        }
        return sessionBean;
    }
    public Object getSessionBean(String beanName) {
        Object sessionBean = null;
        if (beanName != null) {
            sessionBean = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(beanName);
        }
        if (sessionBean == null) {
            exibirMensagem(FacesMessage.SEVERITY_FATAL, "Informação", "Erro interno no servidor, por favor entre em contato com o suporte!");
        }
        return sessionBean;
    }
    public void addValueSession(String key, Object value){
        findValueSession(key,value,"put");
    }
    public void removeValueSession(String key){
        findValueSession(key,null,"remove");
    }
    public Object getSessionValue(String key){
        return findValueSession(key, null, "get");
    }
    private Object findValueSession(String key, Object value, String... action){
        String act  = action != null && action.length > 0 ? action[0] : null;
        Object data = null;
        boolean breakProcess = false;
        Map<String,Object> jsfSession = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        for(Map.Entry<String,Object> values : jsfSession.entrySet()){
            if(values.getKey().equals(key)){//Valor existente na session, removendo valor
                if(act != null && (act.equals("put") || act.equals("remove"))){
                    jsfSession.remove(values.getKey());
                }else{
                    data = jsfSession.get(key);
                }
                breakProcess = true;
            }
            if(breakProcess)
                break;
        }
        if(!breakProcess){
            Logger.getLogger(FacesUtil.class.getSimpleName()).log(Level.INFO, "Registro não encontrado na session ===> {0}",key);
        }
        if(act != null){
            switch(act){
                case "put":
                    jsfSession.put(key, value);
                    break;
                case "get":
                    return data;
                default: break;
            }
        }
        return null;
    }
    
    public void addParamsCallback(RequestContext req, Object[] keys, Object[] values){
        int count = 0;
        HashMap<Object,Object> callbackValues = new HashMap<>();
        for(Object key : keys){
            callbackValues.put(key, values[count]);
            count++;
        }
        this.addParamsCallback(req, callbackValues);
    }
    
    private void addParamsCallback(RequestContext req, HashMap<Object,Object> values){
        for(Map.Entry<Object,Object> value : values.entrySet()){
            req.addCallbackParam(value.getKey().toString(), value.getValue());
        }
    }
    
    public RequestContext getPrimeRequest(){
        return RequestContext.getCurrentInstance();
    }
    
    public void primeUpdateContent(RequestContext req, String... ids){
        for(String id : ids){
            req.update(id);
        }
    }
    
    private Map<Class, Object> getInstances() {
        this.instances = new HashMap<>();
        for(Map.Entry<String,Object> values : FacesContext.getCurrentInstance().getViewRoot().getViewMap().entrySet()){
            this.instances.put(values.getValue().getClass(),values.getValue());
            //System.out.println("Values ===> "+values.getKey() + " - "+values.getValue());
        }
        return instances;
    }
    protected Object[] getInstanceBean(){
        Map<Class,Object> viewsBeans = getInstances();
        Object[] values = new Object[viewsBeans.size()];
        int count = 0;
        for(Map.Entry<Class,Object> instance : viewsBeans.entrySet()){
            values[count] = instance.getValue();
            count++;
        }
        return values;
    }
    
    protected List getInstanceBeanASList(){
         Map<Class,Object> viewsBeans = getInstances();
         List values = new ArrayList();
         for(Map.Entry<Class,Object> instance : viewsBeans.entrySet()){
             values.add(instance.getValue());
         }
         return values;
    }
    
    protected Object getInstanceBean(Class clazz){
        Map<Class,Object> viewsBeans = getInstances();
        for(Map.Entry<Class,Object> instance : viewsBeans.entrySet()){
            if(instance.getKey().equals(clazz)){
                System.out.println("Key ==> "+instance.getValue());
                return instance.getValue();
            }
        }
        return null;
    }
    protected Object getInstanceBean(Class... clasz){
        Map<Class,Object> viewsBeans = getInstances();
        for(Class clazz : clasz){
            for(Map.Entry<Class,Object> instance : viewsBeans.entrySet()){
                if(instance.getKey().equals(clazz)){
                    System.out.println("Key ==> "+instance.getValue());
                    return instance.getValue();
                }
            }
        }
        return null;
    }
    
    protected Object getParamFaces(String key){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }
    
    public Integer rand(){
        Random rand = new Random();
        return rand.nextInt();
    }

    public FacesContext getFacesContext(){
        return FacesContext.getCurrentInstance();
    }
    public ExternalContext getExternaContext(){
        return getFacesContext().getExternalContext();
    }

    public String getAreaAtiva() {
        if(this.areaAtiva == null){
            this.areaAtiva = "";
        }
        return areaAtiva;
    }

    public void setAreaAtiva(String areaAtiva) {
        this.areaAtiva = areaAtiva;
    }

    public String getErrorMessage() {
        if(this.errorMessage == null){
            this.errorMessage = "";
        }
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
