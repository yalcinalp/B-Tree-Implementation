import java.util.ArrayList;
import java.util.Objects;

public class ScholarTree {

	public ScholarNode primaryRoot;		// root of the primary B+ tree
	public ScholarNode secondaryRoot;	// root of the secondary B+ tree

	public ScholarTree(Integer order) {
		ScholarNode.order = order;
		primaryRoot = new ScholarNodePrimaryLeaf(null);
		primaryRoot.level = 0;
		secondaryRoot = new ScholarNodeSecondaryLeaf(null);
		secondaryRoot.level = 0;
	}

	public void addPaper(CengPaper paper) {
		addPaperToPrimary(paper);
		addPaperToSecondary(paper);
	}

	private void addPaperToPrimary(CengPaper paper) {
		ScholarNode node = primaryRoot;

		while (node.getType() == ScholarNodeType.Internal) {
			ScholarNodePrimaryIndex internNod = (ScholarNodePrimaryIndex) node;
			int index = 0;
			while (index < internNod.paperIdCount() && paper.paperId() > internNod.paperIdAtIndex(index)) {
				index++;
			}
			node = internNod.getChildrenAt(index);
		}

		ScholarNodePrimaryLeaf lfNod = (ScholarNodePrimaryLeaf) node;
		int idx = 0;
		while (idx < lfNod.paperCount() && paper.paperId() > lfNod.paperIdAtIndex(idx)) {
			idx++;
		}
		lfNod.addPaper(idx, paper);

		if (lfNod.paperCount() > 2 * ScholarNode.order) {
			splitPrimaryLeaf(lfNod);
		}
	}

	private void splitPrimaryLeaf(ScholarNodePrimaryLeaf leaf) {
		int mid = leaf.paperCount() / 2;

		ArrayList<CengPaper> rightones = new ArrayList<>(leaf.getPapers().subList(mid, leaf.paperCount()));
		leaf.getPapers().subList(mid, leaf.paperCount()).clear();

		ScholarNodePrimaryLeaf newLeaf = new ScholarNodePrimaryLeaf(leaf.getParent(), rightones);
		newLeaf.setParent(leaf.getParent());

		if (leaf.getParent() == null) {
			ScholarNodePrimaryIndex newRoot = new ScholarNodePrimaryIndex(null);
			newRoot.paperIds.add(rightones.get(0).paperId());

			newRoot.getAllChildren().add(leaf);
			newRoot.getAllChildren().add(newLeaf);

			leaf.setParent(newRoot);
			newLeaf.setParent(newRoot);
			primaryRoot = newRoot;

		} else {
			ScholarNodePrimaryIndex parent = (ScholarNodePrimaryIndex) leaf.getParent();
			int insrt_idx = 0;
			while (insrt_idx < parent.paperIdCount() && parent.paperIdAtIndex(insrt_idx) < rightones.get(0).paperId()) {
				insrt_idx++;
			}
			parent.paperIds.add(insrt_idx, rightones.get(0).paperId());
			parent.getAllChildren().add(insrt_idx + 1, newLeaf);

			if (parent.paperIdCount() > 2 * ScholarNode.order) {
				splitPrimaryInternalNode(parent);
			}
		}
	}

	private void splitPrimaryInternalNode(ScholarNodePrimaryIndex node) {
		int mid = node.paperIdCount() / 2;
		ArrayList<Integer> rightPaperIds = new ArrayList<>(node.paperIds.subList(mid + 1, node.paperIdCount()));
		ArrayList<ScholarNode> rightChild = new ArrayList<>(node.getAllChildren().subList(mid + 1, node.getAllChildren().size()));

		ScholarNodePrimaryIndex newInternal = new ScholarNodePrimaryIndex(node.getParent(), rightPaperIds, rightChild);
		for (ScholarNode child : rightChild) {
			child.setParent(newInternal);
		}

		int pushupKey = node.paperIdAtIndex(mid);
		node.paperIds.subList(mid, node.paperIdCount()).clear();
		node.getAllChildren().subList(mid + 1, node.getAllChildren().size()).clear();

		if (node.getParent() == null) {
			ScholarNodePrimaryIndex newRoot = new ScholarNodePrimaryIndex(null);

			newRoot.paperIds.add(pushupKey);
			newRoot.getAllChildren().add(node);
			newRoot.getAllChildren().add(newInternal);

			node.setParent(newRoot);
			newInternal.setParent(newRoot);
			primaryRoot = newRoot;
		} else {
			ScholarNodePrimaryIndex parent = (ScholarNodePrimaryIndex) node.getParent();
			int insrt_idx = 0;
			while (insrt_idx < parent.paperIdCount() && parent.paperIdAtIndex(insrt_idx) < pushupKey) {
				insrt_idx++;
			}
			parent.paperIds.add(insrt_idx, pushupKey);
			parent.getAllChildren().add(insrt_idx + 1, newInternal);

			if (parent.paperIdCount() > 2 * ScholarNode.order) {
				splitPrimaryInternalNode(parent);
			}
		}
	}

	private void addPaperToSecondary(CengPaper paper) {
		ScholarNode node = secondaryRoot;
		while (node.getType() == ScholarNodeType.Internal) {
			ScholarNodeSecondaryIndex internalNode = (ScholarNodeSecondaryIndex) node;
			int index = 0;
			while (index < internalNode.journalCount() && paper.journal().compareTo(internalNode.journalAtIndex(index)) >= 0) {
				index++;
			}
			node = internalNode.getChildrenAt(index);
		}

		ScholarNodeSecondaryLeaf leafNode = (ScholarNodeSecondaryLeaf) node;
		int insrt_idx = 0;
		while (insrt_idx < leafNode.journalCount() && paper.journal().compareTo(leafNode.journalAtIndex(insrt_idx)) > 0) {
			insrt_idx++;
		}
		leafNode.addPaper(insrt_idx, paper);

		if (leafNode.journalCount() > 2 * ScholarNode.order) {
			splitSecondaryLeaf(leafNode);
		}
	}

	private void splitSecondaryLeaf(ScholarNodeSecondaryLeaf leaf) {
		int mid = leaf.journalCount() / 2;

		ArrayList<String> wantedJournals = new ArrayList<>(leaf.getJournals().subList(mid, leaf.journalCount()));
		ArrayList<ArrayList<Integer>> rightBuckets = new ArrayList<>(leaf.getPaperIdBucket().subList(mid, leaf.journalCount()));

		leaf.getJournals().subList(mid, leaf.journalCount()).clear();
		leaf.getPaperIdBucket().subList(mid, leaf.journalCount()).clear();

		ScholarNodeSecondaryLeaf newLeaf = new ScholarNodeSecondaryLeaf(leaf.getParent(), wantedJournals, rightBuckets);
		newLeaf.setParent(leaf.getParent());

		if (leaf.getParent() == null) {
			ScholarNodeSecondaryIndex newRoot = new ScholarNodeSecondaryIndex(null);
			newRoot.journals.add(wantedJournals.get(0));

			newRoot.getAllChildren().add(leaf);
			newRoot.getAllChildren().add(newLeaf);

			leaf.setParent(newRoot);
			newLeaf.setParent(newRoot);
			secondaryRoot = newRoot;
		} else {
			ScholarNodeSecondaryIndex parent = (ScholarNodeSecondaryIndex) leaf.getParent();
			int insrt_idx = 0;
			while (insrt_idx < parent.journalCount() && parent.journalAtIndex(insrt_idx).compareTo(wantedJournals.get(0)) < 0) {
				insrt_idx++;
			}
			parent.journals.add(insrt_idx, wantedJournals.get(0));
			parent.getAllChildren().add(insrt_idx + 1, newLeaf);

			if (parent.journalCount() > 2 * ScholarNode.order) {
				splitSecondaryInternalNode(parent);
			}
		}
	}

	private void splitSecondaryInternalNode(ScholarNodeSecondaryIndex node) {
		int mid = node.journalCount() / 2;
		ArrayList<String> wantedJournals = new ArrayList<>(node.journals.subList(mid + 1, node.journalCount()));
		ArrayList<ScholarNode> rightChild = new ArrayList<>(node.getAllChildren().subList(mid + 1, node.getAllChildren().size()));

		ScholarNodeSecondaryIndex newInternal = new ScholarNodeSecondaryIndex(node.getParent(), wantedJournals, rightChild);
		for (ScholarNode child : rightChild) {
			child.setParent(newInternal);
		}

		String pushupKey = node.journalAtIndex(mid);
		node.journals.subList(mid, node.journalCount()).clear();
		node.getAllChildren().subList(mid + 1, node.getAllChildren().size()).clear();

		if (node.getParent() == null) {
			ScholarNodeSecondaryIndex newRoot = new ScholarNodeSecondaryIndex(null);

			newRoot.journals.add(pushupKey);
			newRoot.getAllChildren().add(node);
			newRoot.getAllChildren().add(newInternal);
			node.setParent(newRoot);

			newInternal.setParent(newRoot);
			secondaryRoot = newRoot;
		} else {
			ScholarNodeSecondaryIndex parent = (ScholarNodeSecondaryIndex) node.getParent();
			int insrt_idx = 0;
			while (insrt_idx < parent.journalCount() && parent.journalAtIndex(insrt_idx).compareTo(pushupKey) < 0) {
				insrt_idx++;
			}
			parent.journals.add(insrt_idx, pushupKey);
			parent.getAllChildren().add(insrt_idx + 1, newInternal);

			if (parent.journalCount() > 2 * ScholarNode.order) {
				splitSecondaryInternalNode(parent);
			}
		}
	}

	private void printIndented(int level, String message) {
		for (int i = 0; i < level; i++) {
			System.out.print("\t");
		}
		System.out.println(message);
	}

	public void searchPaper(Integer paperId) {
		ScholarNode node = primaryRoot;
		int level = 0;
		while (node.getType() == ScholarNodeType.Internal) {
			ScholarNodePrimaryIndex internalNod = (ScholarNodePrimaryIndex) node;
			printIndented(level, "<index>");
			for (int i = 0; i < internalNod.paperIdCount(); i++) {
				printIndented(level, String.valueOf(internalNod.paperIdAtIndex(i)));
			}
			printIndented(level, "</index>");

			int index = 0;
			while (index < internalNod.paperIdCount() && paperId >= internalNod.paperIdAtIndex(index)) {
				index++;
			}
			node = internalNod.getChildrenAt(index);
			level++;
		}

		ScholarNodePrimaryLeaf leafNode = (ScholarNodePrimaryLeaf) node;
		printIndented(level, "<data>");
		for (int i = 0; i < leafNode.paperCount(); i++) {
			if (Objects.equals(leafNode.paperIdAtIndex(i), paperId)) {
				CengPaper paper = leafNode.paperAtIndex(i);
				printIndented(level + 1, "<record>" +
						paper.paperId().toString() + "|" +
						paper.journal() + "|" +
						paper.paperName() + "|" +
						paper.author() + "</record>");
				printIndented(level, "</data>");
				return;
			}
		}
		printIndented(level, "</data>");
		System.out.println("Could not find " + paperId);
	}

	private CengPaper searchPaperFromPrimaryTree(ScholarNode node, Integer paperId) {
		while (node.getType() == ScholarNodeType.Internal) {
			ScholarNodePrimaryIndex internalNod = (ScholarNodePrimaryIndex) node;
			int index = 0;
			while (index < internalNod.paperIdCount() && paperId > internalNod.paperIdAtIndex(index)) {
				index++;
			}
			node = internalNod.getChildrenAt(index);
		}

		ScholarNodePrimaryLeaf leafNod = (ScholarNodePrimaryLeaf) node;
		for (int i = 0; i < leafNod.paperCount(); i++) {
			if (Objects.equals(leafNod.paperIdAtIndex(i), paperId)) {
				return leafNod.paperAtIndex(i);
			}
		}
		return null;
	}

	public void searchJournal(String journal) {
		ScholarNode node = secondaryRoot;
		int level = 0;
		while (node.getType() == ScholarNodeType.Internal) {
			ScholarNodeSecondaryIndex internalNod = (ScholarNodeSecondaryIndex) node;
			printIndented(level, "<index>");
			for (int i = 0; i < internalNod.journalCount(); i++) {
				printIndented(level, internalNod.journalAtIndex(i));
			}
			printIndented(level, "</index>");

			int index = 0;
			while (index < internalNod.journalCount() && journal.compareTo(internalNod.journalAtIndex(index)) > 0) {
				index++;
			}
			node = internalNod.getChildrenAt(index);
			level++;
		}

		ScholarNodeSecondaryLeaf leafNode = (ScholarNodeSecondaryLeaf) node;
		printIndented(level, "<data>");
		for (int i = 0; i < leafNode.journalCount(); i++) {
			if (leafNode.journalAtIndex(i).equalsIgnoreCase(journal)) {
				printIndented(level + 1, journal);
				ArrayList<Integer> paperIds = leafNode.papersAtIndex(i);
				for (Integer paperId : paperIds) {
					CengPaper paper = searchPaperFromPrimaryTree(primaryRoot, paperId);
					if (paper != null) {
						printIndented(level + 1, "<record>" +
								paper.paperId().toString() + "|" +
								paper.journal() + "|" +
								paper.paperName() + "|" +
								paper.author() + "</record>");
					}
				}
				printIndented(level, "</data>");
				return;
			}
		}
		printIndented(level, "</data>");
		System.out.println("Could not find " + journal);
	}

	public void printPrimaryScholar() {
		printPrimaryTree(primaryRoot, 0);
	}

	private void printPrimaryTree(ScholarNode node, int level) {
		if (node.getType() == ScholarNodeType.Leaf) {
			ScholarNodePrimaryLeaf leaf = (ScholarNodePrimaryLeaf) node;
			printIndented(level, "<data>");
			for (CengPaper paper : leaf.getPapers()) {

				String recordData = paper.paperId() + "|"
						+ paper.journal() + "|"
						+ paper.paperName() + "|"
						+ paper.author();
				printIndented(level, "<record>" + recordData + "</record>");
			}
			printIndented(level, "</data>");
		}
		else {
			ScholarNodePrimaryIndex internal = (ScholarNodePrimaryIndex) node;
			printIndented(level, "<index>");
			for (Integer key : internal.paperIds) {
				printIndented(level, String.valueOf(key));
			}
			printIndented(level, "</index>");

			for (ScholarNode child : internal.getAllChildren()) {
				printPrimaryTree(child, level + 1);
			}
		}
	}


	public void printSecondaryScholar() {
		printSecondaryTree(secondaryRoot, 0);
	}

	private void printSecondaryTree(ScholarNode node, int level) {
		if (node.getType() == ScholarNodeType.Leaf) {
			ScholarNodeSecondaryLeaf leaf = (ScholarNodeSecondaryLeaf) node;
			printIndented(level, "<data>");
			for (int i = 0; i < leaf.journalCount(); i++) {
				printIndented(level + 1, leaf.journalAtIndex(i));
				for (Integer paperId : leaf.papersAtIndex(i)) {
					printIndented(level + 2, "<record>" + paperId + "</record>");
				}
			}
			printIndented(level, "</data>");
		} else {
			ScholarNodeSecondaryIndex internal = (ScholarNodeSecondaryIndex) node;
			printIndented(level, "<index>");
			for (String key : internal.journals) {
				printIndented(level + 1, key);
			}
			printIndented(level, "</index>");
			for (ScholarNode child : internal.getAllChildren()) {
				printSecondaryTree(child, level + 1);
			}
		}
	}
}
