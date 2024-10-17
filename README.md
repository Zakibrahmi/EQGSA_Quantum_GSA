# EQGSA_Quantum_GSA
This repository contains code source of our algorithm EQGSA, which is an extension of the algorithm  GSA by Quantum computing. EQGSA is applied to resolve data placement  problem for  scientific workflow (SW) in geo-distributed cloud computing. 


### Citation
Please cite the following reference when using this code:
```bibtex
@article{brahmi2024eqgsa,
  title={EQGSA-DPW: A Quantum-GSA Algorithm-Based Data Placement for Scientific Workflow in Cloud Computing Environment},
  author={Brahmi, Zaki and Derouiche, Rihab},
  journal={Journal of Grid Computing},
  volume={22},
  number={3},
  pages={57},
  year={2024},
  publisher={Springer}
}
```
# How to use the code

**EQGSA is based on the [EARS](https://github.com/UM-LPM/EARS) framework. It is recommended to clone this repository for use.**

The algorithm is implemented in the `IBQIGSA` class, located in the package `EARS/src/org/um/feri/ears/algorithms/so/gsa`. The data placement problem and its objective function are implemented in the `DataPlacementIWF` class, which extends the `Problem` class of EARS. This can be found in `EARS/src/org/um/feri/ears/problems/dataPlacementIWF`.

An example of the algorithm’s execution is provided in the `TestIBQIGSA` class, located at `EARS/src/org/um/feri/ears/problems/dataPlacementIWF`.

**Setup Instructions:**

1. **Create the Cloud Infrastructure:** Use the `CreationVMS.java` class in `src/algoFCA/model/` to create the cloud infrastructure (VMs, datacenters, hosts). You will need to add `cloudSim` (in `.jar` format) and other dependencies, which are available in the `jars` folder. The created cloud infrastructure is stored in a file named `configX.txt`, where `X` represents the number of VMs. An example of how to use `CreationVMS.java` is available in the `initialiseDataCenter.java` class at `src/org/um/feri/ears/problems/dataPlacementIWF/`.

2. **Read Scientific Workflow Information:** The workflow data should be in an XML file (examples are available in the `workflows` folder). To parse the XML file, use the `StaxParser.java` class located in `algoFCA/read/`. For example: 
   ```java
   StaxParser read = new StaxParser("D:\\workflows\\used\\CyberShake_30.xml", 10);
   ```
   Here, `D:\\workflows\\used\\CyberShake_30.xml` is the XML file for the workflow, and `10` is the percentage of the fixed dataset.

3. **Run the Algorithm:** After initialization is complete, you can run the EQGSA algorithm as well as other optimization algorithms available in the EARS framework, such as GSA and PSO.

Feel free to contact me for any additional information at zakibrahmi at gmail.com

