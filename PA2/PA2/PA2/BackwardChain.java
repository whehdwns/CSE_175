//
// BackwardChain
//
// This class implements a backward chaining inference procedure. The
// implementation is very skeletal, and the resulting reasoning process is
// not particularly efficient. Knowledge is restricted to the form of
// definite clauses, grouped into a list of positive literals (facts) and
// a list of Horn clause implications (rules). The inference procedure
// maintains a list of goals. On each step, a proof is sought for the
// first goal in this list, starting by an attempt to unify the goal with
// any known fact in the knowledge base. If this fails, the rules are
// examined in the order in which they appear in the knowledge base, searching
// for a consequent that unifies with the goal. Upon successful unification,
// a proof is sought for the conjunction of the rule antecedents. If this
// fails, further rules are considered. Note that this is a strictly
// depth-first approach, so it is incomplete. Note, also, that there is
// no backtracking with regard to matches to facts -- the first match is
// always taken and other potential matches are ignored. This can make
// the algorithm incomplete in another way. In short, the order in which
// facts and rules appear in the knowledge base can have a large influence
// on the behavior of this inference procedure.
//
// In order to use this inference engine, the knowledge base must be
// initialized by a call to "initKB". Queries are then submitted using the
// "ask" method. The "ask" function returns a binding list which includes
// bindings for intermediate variables.
//
// David Noelle -- Tue Oct  9 18:48:57 PDT 2018
//


import java.util.*;


public class BackwardChain {

    public KnowledgeBase kb;

	// Default constructor ...
	public BackwardChain() {
		this.kb = new KnowledgeBase();
	}

	// initKB -- Initialize the knowledge base by interactively requesting
	// file names and reading those files. Return false on error.
	public boolean initKB() {
		return (kb.readKB());
	}

	// unify -- Return the most general unifier for the two provided literals,
	// or null if no unification is possible. The returned binding list
	// should be freshly allocated.
	
	//bl is theta
	// AIMA
	// 		Page 325 - 329
	//		page 294 in Section 8.2.3
	
	// function UNIFY(x, y, theta) 
	// 	if theta is failure then return null
	//	else if x = y return theta
	//	else if VARIABLE?(x) then return UNIFY-VAR(x, y, theta)
	// 	else if VARIABLE?(y) then return UNIFY-VAR(y, x, theta)
	//	else if Compound?(x) and Compound?(y)
	//		return UNIFY(x.args, y.args, UNIFY(x.op, y.op, theta)
	//	else if list?(x) and list?(y)
	// 		return UNIFY(x.rest, y.rest, UNIFY(x.first, y.first. theta)
	//else fail
	
	// function UNIFY-VAR(var, x, theta)
	// 	if {var/val} ? theta then return UNIFY(val, x, theta)
		// x is a variable
	// 	else if {x/val} ? theta then return UNIFY(var, val, theta)
		// x is a function
	// 	else if OCCUR-CHECK?(var, x) then return failure
	// 	else return add {var/x} to theta . newBL.addVariableBinding(var,x);
	
	public BindingList unify(Literal lit1, Literal lit2, BindingList bl) {
		// PROVIDE YOUR CODE HERE!
		// if BindingList b1 is null then return null
		if(bl == null) {
			return null;
		}
		//if literal x and y is equal to each other then return unify function
		if (lit1.pred.equals(lit2.pred)) {
			return unify(lit1.args, lit2.args, bl);
		}
		
		return (null);
	}

	// unify -- Return the most general unifier for the two provided terms,
	// or null if no unification is possible. The returned binding list
	// should be freshly allocated.
	public BindingList unify(Term t1, Term t2, BindingList bl) {

		// PROVIDE YOUR CODE HERE!
		//if theta = failure then return null
		if(bl == null) {
			return null;
		}
		///if x = y then return theta
		if (t1.equals(t2)) {
			return bl;
		}
		//Types of Variable : Constant/ Variable/ Function
		//Constant
		if(t1.c !=null) {
			if(t2.c!=null) {
				if(t1.equals(t2)){
					return bl;
				}
			}
			//if t2 is variable, then it can bind with t1, and constant
			//if VARIABLE?(y) then return UNIFY-VAR(y, x, theta)
			else if(t2.v!=null) {
				return unify(t2, t1, bl);
			}
		}
		//Variable
		//Using UNIFY-VAR method 
		if(t1.v!=null) {
			//temp1: Search this binding list for a value corresponding to the given variable. 
			//Since Unify function contains (term, term, binding list)
			//We need to change t1.v to temp1 so it can fit into term variable.
			Term temp1 = bl.boundValue(t1.v);
			//VARIABLE?(x)
			//UNIFY-VAR(x, y, theta)
			if(temp1 != null) {
				return unify(t2, temp1, bl);
			}else if(t2.c != null) {
				bl.addVariableBinding(t1.v, t2);
				return bl;
			}
			//VARIABLE?(y)
			// UNIFY-VAR(y, x, theta)
			if(t2.v!=null) {
				if(t1.equals(t2)) {
					return bl;
				}
				//temp1: Search this binding list for a value corresponding to the given variable. 
				//Since Unify function contains (term, term, binding list)
				//We need to change t2.v to temp2 so it can fit into term variable.
				Term temp2 = bl.boundValue(t2.v);
				if(temp2 != null) {
					return unify(t1, temp2, bl);
				}else {
					bl.addVariableBinding(t2.v, t1);
					return bl;
				}
			}
		}
		
		//Function
		if(t1.f!=null) {
			if(t2.f!=null) {
				//constant and a function  can never be unified, other can be unified
				return null;
			}else if(t2.v!=null) {
				//if VARIABLE?(y) then return UNIFY-VAR(y, x, theta)
				return unify(t2, t1, bl);
			}else if(t2.f!=null){
			// if VARIABLE?(x) then return UNIFY-VAR(x, y, theta)
				return unify(t1.f, t2.f, bl);
			}
		}
		
		
		return (null);
	}

	// unify -- Return the most general unifier for the two provided functions,
	// or null if no unification is possible. The returned binding list
	// should be freshly allocated.
	public BindingList unify(Function f1, Function f2, BindingList bl) {

		// PROVIDE YOUR CODE HERE!
		//if bl is null then return null
		if(bl == null) {
			return null;
		}
		//if two function is equal to each other then return unify function
		if(f1.func.equals(f2)) {
			return unify(f1.args, f2.args, bl);
		}
		
		return (null);
	}

	// unify -- Return the most general unifier for the two provided lists of
	// terms, or null if no unification is possible. The returned binding
	// list should be freshly allocated.
	public BindingList unify(List<Term> ts1, List<Term> ts2, BindingList bl) {
		if (bl == null)
			return (null);
		if ((ts1.size() == 0) && (ts2.size() == 0))
			// Empty lists match other empty lists ...
			return (new BindingList(bl));
		if ((ts1.size() == 0) || (ts2.size() == 0))
			// Ran out of arguments in one list before reaching the
			// end of the other list, which means the two argument lists
			// can't match ...
			return (null);
		List<Term> terms1 = new LinkedList<Term>();
		List<Term> terms2 = new LinkedList<Term>();
		terms1.addAll(ts1);
		terms2.addAll(ts2);
		Term t1 = terms1.get(0);
		Term t2 = terms2.get(0);
		terms1.remove(0);
		terms2.remove(0);
		return (unify(terms1, terms2, unify(t1, t2, bl)));
	}

	// askFacts -- Examine all of the facts in the knowledge base to
	// determine if any of them unify with the given literal, under the
	// given binding list. If a unification is found, return the
	// corresponding most general unifier. If none is found, return null
	// to indicate failure.
	BindingList askFacts(Literal lit, BindingList bl) {
		BindingList mgu = null; // Most General Unifier
		for (Literal fact : kb.facts) {
			mgu = unify(lit, fact, bl);
			if (mgu != null)
				return (mgu);
		}
		return (null);
	}

	// askFacts -- Examine all of the facts in the knowledge base to
	// determine if any of them unify with the given literal. If a
	// unification is found, return the corresponding most general unifier.
	// If none is found, return null to indicate failure.
	BindingList askFacts(Literal lit) {
		return (askFacts(lit, new BindingList()));
	}

	// ask -- Try to prove the given goal literal, under the constraints of
	// the given binding list, using both the list of known facts and the
	// collection of known rules. Terminate as soon as a proof is found,
	// returning the resulting binding list for that proof. Return null if
	// no proof can be found. The returned binding list should be freshly
	// allocated.
	BindingList ask(Literal goal, BindingList bl) {
		BindingList result = askFacts(goal, bl);
		if (result != null) {
			// The literal can be unified with a known fact ...
			return (result);
		}
		// Need to look at rules ...
		for (Rule candidateRule : kb.rules) {
			if (candidateRule.consequent.pred.equals(goal.pred)) {
				// The rule head uses the same predicate as the goal ...
				// Standardize apart ...
				Rule r = candidateRule.standardizeApart();
				// Check to see if the consequent unifies with the goal ...
				result = unify(goal, r.consequent, bl);
				if (result != null) {
					// This rule might be part of a proof, if we can prove
					// the rule's antecedents ...
					result = ask(r.antecedents, result);
					if (result != null) {
						// The antecedents have been proven, so the goal
						// is proven ...
						return (result);
					}
				}
			}
		}
		// No rule that matches has antecedents that can be proven. Thus,
		// the search fails ...
		return (null);
	}

	// ask -- Try to prove the given goal literal using both the list of
	// known facts and the collection of known rules. Terminate as soon as
	// a proof is found, returning the resulting binding list for that proof.
	// Return null if no proof can be found. The returned binding list
	// should be freshly allocated.
	BindingList ask(Literal goal) {
		return (ask(goal, new BindingList()));
	}

	// ask -- Try to prove the given list of goal literals, under the
	// constraints of the given binding list, using both the list of known
	// facts and the collection of known rules. Terminate as soon as a proof
	// is found, returning the resulting binding list for that proof. Return
	// null if no proof can be found. The returned binding list should be
	// freshly allocated.
	BindingList ask(List<Literal> goals, BindingList bl) {
		if (goals.size() == 0) {
			// All goals have been satisfied ...
			return (bl);
		} else {
			List<Literal> newGoals = new LinkedList<Literal>();
			newGoals.addAll(goals);
			Literal goal = newGoals.get(0);
			newGoals.remove(0);
			BindingList firstBL = ask(goal, bl);
			if (firstBL == null) {
				// Failure to prove one of the goals ...
				return (null);
			} else {
				// Try to prove the remaining goals ...
				return (ask(newGoals, firstBL));
			}
		}
	}

}
