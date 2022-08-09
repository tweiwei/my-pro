
module mem_wb_ctrl(
  input   clk,
  input   reset,
  input   in_wb_ctrl_toReg,
  input   in_wb_ctrl_regWrite,
  input   in_noflush,
  input   flush,
  input   valid,
  output  data_wb_ctrl_toReg,
  output  data_wb_ctrl_regWrite,
  output  data_noflush
);

  reg  reg_wb_ctrl_toReg; 
  reg  reg_wb_ctrl_regWrite; 
  reg  reg_noflush; 

  assign data_wb_ctrl_toReg = reg_wb_ctrl_toReg; 
  assign data_wb_ctrl_regWrite = reg_wb_ctrl_regWrite; 
  assign data_noflush = reg_noflush; 

  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_wb_ctrl_toReg <= 1'h0; 
    end else if (flush) begin 
      reg_wb_ctrl_toReg <= 1'h0; 
    end else if (valid) begin 
      reg_wb_ctrl_toReg <= in_wb_ctrl_toReg; 
    end
  end

  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_wb_ctrl_regWrite <= 1'h0; 
    end else if (flush) begin 
      reg_wb_ctrl_regWrite <= 1'h0; 
    end else if (valid) begin 
      reg_wb_ctrl_regWrite <= in_wb_ctrl_regWrite; 
    end
  end

  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_noflush <= 1'h0; 
    end else if (flush) begin 
      reg_noflush <= 1'h0; 
    end else if (valid) begin 
      reg_noflush <= in_noflush; 
    end
  end

endmodule