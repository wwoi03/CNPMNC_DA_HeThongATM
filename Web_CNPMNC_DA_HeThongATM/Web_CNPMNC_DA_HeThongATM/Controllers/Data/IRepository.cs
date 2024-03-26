namespace Web_CNPMNC_DA_HeThongATM.Controllers.Data
{
    public interface IRepository<T>
    {
        IEnumerable<T> GetAll();
        T GetById(string key);
        void Insert(T entity);
        void Update(T entity);
        void Delete(string key);
    }
}
