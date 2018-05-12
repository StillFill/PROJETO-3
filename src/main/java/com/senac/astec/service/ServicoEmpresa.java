package com.senac.astec.service;
import com.senac.astec.dao.EmpresaDAO;
import com.senac.astec.model.Empresa;
import java.io.IOException;

/**
 *
 * @author ana.cacosta1
 */
public class ServicoEmpresa {
    EmpresaDAO empresaDAO = new EmpresaDAO();
    
    public int cadastrarEmpresa(Empresa empresa) throws IOException, Exception {


        try {
           int insertedId = empresaDAO.inserirEmpresa(empresa);
            System.out.println("NOVO ID: " + insertedId);
           return insertedId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
}