namespace Web_CNPMNC_DA_HeThongATM.Designpattern.CommandPattern
{
    public class CommandInvoker
    {
        private readonly ICommand _command;

        public CommandInvoker(ICommand command)
        {
            _command = command;
        }

        public void ExecuteCommand()
        {
            _command.Execute();
        }
    }
}
