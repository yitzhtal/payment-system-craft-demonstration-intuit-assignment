package interfaces;

import beans.ProcessedPayment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface DBServiceInf extends CrudRepository<ProcessedPayment,String> {

}
