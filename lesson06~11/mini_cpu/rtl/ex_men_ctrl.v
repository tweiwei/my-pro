
module ex_mem_ctrl(
  input        clk,
  input        reset,
  input        in_mem_ctrl_memRead,
  input        in_mem_ctrl_memWrite,
  input        in_mem_ctrl_taken,
  input  [1:0] in_mem_ctrl_maskMode,
  input        in_mem_ctrl_sext,
  input        in_wb_ctrl_toReg,
  input        in_wb_ctrl_regWrite,
  input        in_noflush,
  input        flush,
  input        valid,
  output       data_mem_ctrl_memRead,
  output       data_mem_ctrl_memWrite,
  output       data_mem_ctrl_taken,
  output [1:0] data_mem_ctrl_maskMode,
  output       data_mem_ctrl_sext,
  output       data_wb_ctrl_toReg,
  output       data_wb_ctrl_regWrite,
  output       data_noflush
);
  reg  reg_mem_ctrl_memRead; 
  reg  reg_mem_ctrl_memWrite; 
  reg  reg_mem_ctrl_taken; 
  reg [1:0] reg_mem_ctrl_maskMode; 
  reg  reg_mem_ctrl_sext; 
  reg  reg_wb_ctrl_toReg; 
  reg  reg_wb_ctrl_regWrite; 
  reg  reg_noflush; 
  assign data_mem_ctrl_memRead = reg_mem_ctrl_memRead; 
  assign data_mem_ctrl_memWrite = reg_mem_ctrl_memWrite; 
  assign data_mem_ctrl_taken = reg_mem_ctrl_taken; 
  assign data_mem_ctrl_maskMode = reg_mem_ctrl_maskMode; 
  assign data_mem_ctrl_sext = reg_mem_ctrl_sext; 
  assign data_wb_ctrl_toReg = reg_wb_ctrl_toReg; 
  assign data_wb_ctrl_regWrite = reg_wb_ctrl_regWrite; 
  assign data_noflush = reg_noflush; 
  
  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_mem_ctrl_memRead <= 1'h0; 
    end else if (flush) begin 
      reg_mem_ctrl_memRead <= 1'h0; 
    end else if (valid) begin 
      reg_mem_ctrl_memRead <= in_mem_ctrl_memRead; 
    end
  end

  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_mem_ctrl_memWrite <= 1'h0; 
    end else if (flush) begin 
      reg_mem_ctrl_memWrite <= 1'h0; 
    end else if (valid) begin 
      reg_mem_ctrl_memWrite <= in_mem_ctrl_memWrite; 
    end
  end

  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_mem_ctrl_taken <= 1'h0; 
    end else if (flush) begin 
      reg_mem_ctrl_taken <= 1'h0; 
    end else if (valid) begin 
      reg_mem_ctrl_taken <= in_mem_ctrl_taken; 
    end
  end

  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_mem_ctrl_maskMode <= 2'h0; 
    end else if (flush) begin 
      reg_mem_ctrl_maskMode <= 2'h0; 
    end else if (valid) begin 
      reg_mem_ctrl_maskMode <= in_mem_ctrl_maskMode; 
    end
  end

  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_mem_ctrl_sext <= 1'h0; 
    end else if (flush) begin 
      reg_mem_ctrl_sext <= 1'h0; 
    end else if (valid) begin 
      reg_mem_ctrl_sext <= in_mem_ctrl_sext; 
    end
  end

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