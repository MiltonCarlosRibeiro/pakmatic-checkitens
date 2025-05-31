// Alterna campos manuais
document.getElementById("modoAutomatico").addEventListener("change", function () {
    const manualFields = document.querySelector(".modoManualCampos");
    manualFields.style.display = this.checked ? "none" : "block";
});

// Envio do formulário de varredura
document.getElementById("formVarredura").addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target;
    const formData = new FormData(form);
    const modoAutomatico = document.getElementById("modoAutomatico").checked;
    formData.append("modoAutomatico", modoAutomatico);

    const resultadoDiv = document.getElementById("resultadoComparativo");
    resultadoDiv.innerHTML = "🔄 Processando...";

    try {
        const response = await fetch("/api/varredura-comparativa", {
            method: "POST",
            body: formData
        });

        if (!response.ok) throw new Error("Falha no servidor");

        const resultado = await response.json();
        resultadoDiv.innerHTML = `
      <h3>📋 Resultado da Varredura</h3>
      <details open><summary>🧾 Em Ambas</summary><p>${resultado.emAmbas.join(", ")}</p></details>
      <details><summary>📤 Só na Tabela 1</summary><p>${resultado.apenasNaTabela1.join(", ")}</p></details>
      <details><summary>📥 Só na Tabela 2</summary><p>${resultado.apenasNaTabela2.join(", ")}</p></details>
      <details><summary>⚠️ Quantidade Diferente</summary><p>${resultado.quantidadeDiferente.join(", ")}</p></details>
    `;
    } catch (err) {
        resultadoDiv.innerHTML = "❌ Erro ao processar a varredura.";
    }
});

// Exportações
function baixarXLSX() {
    window.open("/api/exportar-comparacao", "_blank");
}

function baixarPDF() {
    window.open("/api/exportar-pdf", "_blank");
}
