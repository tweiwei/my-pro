
module ex_mem(
  input         clk,
  input         reset,
  input  [4:0]  in_regWAddr,
  input  [31:0] in_regRData2,
  input  [1:0]  ex_result_sel,
  input  [31:0] in_pc_next,
  input  [31:0] id_ex_data_imm,
  input  [31:0] alu_result,
  input  [31:0] in_pc,
  input         flush,
  input         valid,
  output [4:0]  data_regWAddr,
  output [31:0] data_regRData2,
  output [31:0] data_result,
  output [31:0] data_pc
);

  reg [4:0] reg_regWAddr; 
  reg [31:0] reg_regRData2; 
  reg [31:0] reg_result; 
  reg [31:0] reg_pc; 

  wire [31:0] resulet_w = (ex_result_sel == 2'h0) ? alu_result :
                          (ex_result_sel == 2'h1) ? id_ex_data_imm :
                          (ex_result_sel == 2'h1) ? in_pc_next : 32'h0;

  assign data_regWAddr = reg_regWAddr; 
  assign data_regRData2 = reg_regRData2; 
  assign data_result = reg_result; 
  assign data_pc = reg_pc; 

  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_regWAddr <= 5'h0; 
    end else if (flush) begin 
      reg_regWAddr <= 5'h0; 
    end else if (valid) begin 
      reg_regWAddr <= in_regWAddr; 
    end
  end

  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_regRData2 <= 32'h0; 
    end else if (flush) begin 
      reg_regRData2 <= 32'h0; 
    end else if (valid) begin 
      reg_regRData2 <= in_regRData2; 
    end
  end

  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_result <= 32'h0; 
    end else if (flush) begin 
      reg_result <= 32'h0; 
    end else if (valid) begin 
      reg_result <= resulet_w; 
    end
  end

  always @(posedge clk or posedge reset) begin
    if (reset) begin 
      reg_pc <= 32'h0; 
    end else if (flush) begin 
      reg_pc <= 32'h0; 
    end else if (valid) begin 
      reg_pc <= in_pc; 
    end
  end

endmodule